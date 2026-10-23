package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;
import com.clafaleo.reencaminhaqui.entities.enums.StatusEnvio;
import com.clafaleo.reencaminhaqui.entities.enums.StatusRota;
import com.clafaleo.reencaminhaqui.provedores.MensagemRecebida;
import com.clafaleo.reencaminhaqui.provedores.ProvedorMensageria;
import com.clafaleo.reencaminhaqui.repositories.MensagemRepository;
import com.clafaleo.reencaminhaqui.repositories.ReencaminhamentoRepository;
import com.clafaleo.reencaminhaqui.repositories.RotaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.clafaleo.reencaminhaqui.entities.enums.TipoConteudo;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ReencaminhamentoOrquestrador {

    private final ProvedorRegistry registry;
    private final RotaRepository rotaRepository;
    private final MensagemRepository mensagemRepository;
    private final ReencaminhamentoRepository reencaminhamentoRepository;
    private final ApplicationEventPublisher publisher;

    public ReencaminhamentoOrquestrador(ProvedorRegistry registry,
                                        RotaRepository rotaRepository,
                                        MensagemRepository mensagemRepository,
                                        ReencaminhamentoRepository reencaminhamentoRepository,
                                        ApplicationEventPublisher publisher) {
        this.registry = registry;
        this.rotaRepository = rotaRepository;
        this.mensagemRepository = mensagemRepository;
        this.reencaminhamentoRepository = reencaminhamentoRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void processar(String provedorNome, String corpo, Map<String, String> headers) {
        ProvedorMensageria provedor = registry.get(provedorNome);
        if (!provedor.assinaturaValida(corpo, headers)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Assinatura inválida");
        }

        for (MensagemRecebida rec : provedor.interpretar(corpo)) {
            // idempotência: o provedor pode reenviar o mesmo webhook
            if (rec.idExterno() != null && mensagemRepository.existsByIdExterno(rec.idExterno())) continue;

            Rota rota = rotaRepository
                    .findByNumeroOrigemAndStatusRota(rec.numeroOrigem(), StatusRota.ATIVA)
                    .orElse(null);
            if (rota == null) continue;                                    // número sem rota ativa

            // canal desativado: não reencaminha
            if (rota.getCanal() == null || rota.getCanal().getStatusCanal() != StatusCanal.ATIVO) continue;

            // evita loop: se B responde ao A, não reenvia de volta para B
            //if (rec.remetente().equals(rota.getNumeroDestino())) continue;

            Mensagem m = new Mensagem();
            m.setIdExterno(rec.idExterno());
            m.setRemetenteOriginal(rec.remetente());
            m.setConteudoTexto(rec.texto());
            m.setPayloadBruto(rec.payloadBruto());
            m.setTipoConteudo(TipoConteudo.TEXTO);
            m.setRota(rota);
            m.setRecebidoEm(LocalDateTime.now());
            m = mensagemRepository.save(m);

            Reencaminhamento r = new Reencaminhamento();
            r.setMensagem(m);
            r.setStatusEnvio(StatusEnvio.PENDENTE);
            r.setTentativas(0);
            r = reencaminhamentoRepository.save(r);

            publisher.publishEvent(new ReencaminhamentoCriado(r.getId()));
        }
    }
}