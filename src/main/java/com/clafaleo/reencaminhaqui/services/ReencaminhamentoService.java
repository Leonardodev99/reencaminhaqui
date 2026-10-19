package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.dto.ReencaminhamentoUpdateDTO;
import com.clafaleo.reencaminhaqui.repositories.MensagemRepository;
import com.clafaleo.reencaminhaqui.repositories.ReencaminhamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReencaminhamentoService {

    private final ReencaminhamentoRepository reencaminhamentoRepository;
    private final MensagemRepository mensagemRepository;

    public ReencaminhamentoService(ReencaminhamentoRepository reencaminhamentoRepository,
                                   MensagemRepository mensagemRepository) {
        this.reencaminhamentoRepository = reencaminhamentoRepository;
        this.mensagemRepository = mensagemRepository;
    }

    @Transactional
    public Reencaminhamento salvar(Reencaminhamento r) {
        // se foi informada a mensagem por id, valida existência e seta a entidade completa
        if (r.getMensagem() != null && r.getMensagem().getId() != null) {
            Long mensagemId = r.getMensagem().getId();
            Mensagem m = mensagemRepository.findById(mensagemId)
                    .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com id: " + mensagemId));
            r.setMensagem(m);
        }
        return reencaminhamentoRepository.save(r);
    }

    @Transactional(readOnly = true)
    public List<Reencaminhamento> listarTodos() {
        return reencaminhamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Reencaminhamento> listarTodos(Pageable pageable) {
        return reencaminhamentoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Reencaminhamento> listarPorMensagem(Long mensagemId) {
        return reencaminhamentoRepository.findByMensagemId(mensagemId);
    }

    @Transactional(readOnly = true)
    public Page<Reencaminhamento> listarPorMensagem(Long mensagemId, Pageable pageable) {
        return reencaminhamentoRepository.findByMensagemId(mensagemId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Reencaminhamento> buscarPorId(Long id) {
        return reencaminhamentoRepository.findById(id);
    }

    @Transactional
    public Reencaminhamento atualizar(Long id, Reencaminhamento dadosAtualizados) {
        Reencaminhamento existente = reencaminhamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reencaminhamento não encontrado com id: " + id));

        existente.setTentativas(dadosAtualizados.getTentativas());
        existente.setIdTransacaoProvedor(dadosAtualizados.getIdTransacaoProvedor());
        existente.setMensagemErro(dadosAtualizados.getMensagemErro());
        existente.setProcessadoEm(dadosAtualizados.getProcessadoEm());
        existente.setStatusEnvio(dadosAtualizados.getStatusEnvio());

        if (dadosAtualizados.getMensagem() != null && dadosAtualizados.getMensagem().getId() != null) {
            Long mensagemId = dadosAtualizados.getMensagem().getId();
            Mensagem m = mensagemRepository.findById(mensagemId)
                    .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com id: " + mensagemId));
            existente.setMensagem(m);
        }

        return reencaminhamentoRepository.save(existente);
    }

    @Transactional
    public Reencaminhamento atualizarParcial(Long id, ReencaminhamentoUpdateDTO dto) {
        Reencaminhamento existente = reencaminhamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reencaminhamento não encontrado com id: " + id));

        if (dto.getTentativas() != null) {
            existente.setTentativas(dto.getTentativas());
        }
        if (dto.getIdTransacaoProvedor() != null) {
            existente.setIdTransacaoProvedor(dto.getIdTransacaoProvedor());
        }
        if (dto.getMensagemErro() != null) {
            existente.setMensagemErro(dto.getMensagemErro());
        }
        if (dto.getProcessadoEm() != null) {
            existente.setProcessadoEm(dto.getProcessadoEm());
        }
        if (dto.getStatusEnvio() != null) {
            existente.setStatusEnvio(dto.getStatusEnvio());
        }
        if (dto.getMensagemId() != null) {
            Mensagem mensagem = mensagemRepository.findById(dto.getMensagemId())
                    .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com id: " + dto.getMensagemId()));
            existente.setMensagem(mensagem);
        }

        return reencaminhamentoRepository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        if (!reencaminhamentoRepository.existsById(id)) {
            throw new RuntimeException("Reencaminhamento não encontrado com id: " + id);
        }
        reencaminhamentoRepository.deleteById(id);
    }
}