package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.dto.MensagemUpdateDTO;
import com.clafaleo.reencaminhaqui.repositories.MensagemRepository;
import com.clafaleo.reencaminhaqui.repositories.RotaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final RotaRepository rotaRepository;

    public MensagemService(MensagemRepository mensagemRepository, RotaRepository rotaRepository) {
        this.mensagemRepository = mensagemRepository;
        this.rotaRepository = rotaRepository;
    }

    @Transactional
    public Mensagem salvar(Mensagem mensagem) {
        // validação básica: se informado rotaId via relação, garantir que a Rota exista
        if (mensagem.getRota() != null && mensagem.getRota().getId() != null) {
            Long rotaId = mensagem.getRota().getId();
            Rota rota = rotaRepository.findById(rotaId)
                    .orElseThrow(() -> new RuntimeException("Rota não encontrada com id: " + rotaId));
            mensagem.setRota(rota);
        }

        if (mensagem.getRecebidoEm() == null) {
            mensagem.setRecebidoEm(LocalDateTime.now());
        }
        return mensagemRepository.save(mensagem);
    }

    @Transactional(readOnly = true)
    public List<Mensagem> listarTodos() {
        return mensagemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Mensagem> listarTodos(Pageable pageable) {
        return mensagemRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Mensagem> listarPorRota(Long rotaId) {
        return mensagemRepository.findByRotaId(rotaId);
    }

    @Transactional(readOnly = true)
    public Page<Mensagem> listarPorRota(Long rotaId, Pageable pageable) {
        return mensagemRepository.findByRotaId(rotaId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Mensagem> buscarPorId(Long id) {
        return mensagemRepository.findById(id);
    }

    @Transactional
    public Mensagem atualizar(Long id, Mensagem dadosAtualizados) {
        Mensagem existente = mensagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com id: " + id));

        existente.setRemetenteOriginal(dadosAtualizados.getRemetenteOriginal());
        existente.setConteudoTexto(dadosAtualizados.getConteudoTexto());
        existente.setPayloadBruto(dadosAtualizados.getPayloadBruto());
        existente.setRecebidoEm(dadosAtualizados.getRecebidoEm());
        existente.setTipoConteudo(dadosAtualizados.getTipoConteudo());

        if (dadosAtualizados.getRota() != null && dadosAtualizados.getRota().getId() != null) {
            Long rotaId = dadosAtualizados.getRota().getId();
            Rota rota = rotaRepository.findById(rotaId)
                    .orElseThrow(() -> new RuntimeException("Rota não encontrada com id: " + rotaId));
            existente.setRota(rota);
        }

        // anexos e reencaminhamentos são gerenciados por endpoints próprios idealmente;
        // aqui não substituímos listas automaticamente para evitar inconsistências.
        return mensagemRepository.save(existente);
    }

    @Transactional
public Mensagem atualizarParcial(Long id, MensagemUpdateDTO dto) {
    Mensagem existente = mensagemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com id: " + id));

    if (dto.getRemetenteOriginal() != null) {
        existente.setRemetenteOriginal(dto.getRemetenteOriginal());
    }
    if (dto.getConteudoTexto() != null) {
        existente.setConteudoTexto(dto.getConteudoTexto());
    }
    if (dto.getPayloadBruto() != null) {
        existente.setPayloadBruto(dto.getPayloadBruto());
    }
    if (dto.getRecebidoEm() != null) {
        existente.setRecebidoEm(dto.getRecebidoEm());
    }
    if (dto.getTipoConteudo() != null) {
        existente.setTipoConteudo(dto.getTipoConteudo());
    }
    if (dto.getRotaId() != null) {
        Long rotaId = dto.getRotaId();
        Rota rota = rotaRepository.findById(rotaId)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com id: " + rotaId));
        existente.setRota(rota);
    }

    return mensagemRepository.save(existente);
}

    @Transactional
    public void excluir(Long id) {
        if (!mensagemRepository.existsById(id)) {
            throw new RuntimeException("Mensagem não encontrada com id: " + id);
        }
        mensagemRepository.deleteById(id);
    }
}