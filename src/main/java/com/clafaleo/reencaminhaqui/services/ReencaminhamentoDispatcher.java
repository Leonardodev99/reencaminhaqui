package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.enums.StatusEnvio;
import com.clafaleo.reencaminhaqui.provedores.MensagemSaida;
import com.clafaleo.reencaminhaqui.provedores.ProvedorMensageria;
import com.clafaleo.reencaminhaqui.repositories.ReencaminhamentoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReencaminhamentoDispatcher {

    private static final int MAX_TENTATIVAS = 5;

    private final ReencaminhamentoRepository reencaminhamentoRepository;
    private final ProvedorRegistry registry;

    public ReencaminhamentoDispatcher(ReencaminhamentoRepository reencaminhamentoRepository,
                                      ProvedorRegistry registry) {
        this.reencaminhamentoRepository = reencaminhamentoRepository;
        this.registry = registry;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoCriar(ReencaminhamentoCriado e) {
        enviar(e.reencaminhamentoId());
    }

    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void retentarFalhas() {
        reencaminhamentoRepository
                .findByStatusEnvioInAndTentativasLessThan(
                        List.of(StatusEnvio.PENDENTE, StatusEnvio.RETENTANDO), MAX_TENTATIVAS)
                .forEach(r -> enviar(r.getId()));
    }

    @Transactional
    public void enviar(Long id) {
        Reencaminhamento r = reencaminhamentoRepository.findById(id).orElseThrow();
        Mensagem m = r.getMensagem();
        Rota rota = m.getRota();
        r.setTentativas(r.getTentativas() + 1);
        try {
            String texto = "[Mensagem de " + m.getRemetenteOriginal() + "]: " + m.getConteudoTexto();
            ProvedorMensageria p = registry.get(rota.getCanal().getProvedor());

            String idTransacao = p.enviar(new MensagemSaida(
                    rota.getNumeroOrigem(), rota.getNumeroDestino(), texto, List.of()));

            r.setIdTransacaoProvedor(idTransacao);
            r.setStatusEnvio(StatusEnvio.ENVIADO);
            r.setMensagemErro(null);
        } catch (Exception ex) {
            r.setStatusEnvio(r.getTentativas() >= MAX_TENTATIVAS ? StatusEnvio.FALHO : StatusEnvio.RETENTANDO);
            r.setMensagemErro(ex.getMessage());
        }
        r.setProcessadoEm(LocalDateTime.now());
        reencaminhamentoRepository.save(r);
    }
}