package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Canal;
import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.repositories.RotaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RotaService {

    private final RotaRepository rotaRepository;

    public RotaService(RotaRepository rotaRepository) {
        this.rotaRepository = rotaRepository;
    }

    @Transactional
    public Rota salvar(Rota rota) {
        if (rota.getCriadoEm() == null) {
            rota.setCriadoEm(LocalDateTime.now());
        }
        return rotaRepository.save(rota);
    }

    @Transactional(readOnly = true)
    public List<Rota> listarTodos() {
        return rotaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Rota> listarTodos(Pageable pageable) {
        return rotaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Rota> listarPorCanal(Long canalId) {
        return rotaRepository.findByCanalId(canalId);
    }

    @Transactional(readOnly = true)
    public Page<Rota> listarPorCanal(Long canalId, Pageable pageable) {
        return rotaRepository.findByCanalId(canalId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Rota> buscarPorId(Long id) {
        return rotaRepository.findById(id);
    }

    @Transactional
    public Rota atualizar(Long id, Rota dadosAtualizados) {
        Rota rotaExistente = rotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com id: " + id));

        rotaExistente.setNumeroOrigem(dadosAtualizados.getNumeroOrigem());
        rotaExistente.setNumeroDestino(dadosAtualizados.getNumeroDestino());
        rotaExistente.setStatusRota(dadosAtualizados.getStatusRota());
        rotaExistente.setCriadoEm(dadosAtualizados.getCriadoEm());

        // Atualiza FK de canal se informado
        if (dadosAtualizados.getCanal() != null) {
            Canal c = new Canal();
            c.setId(dadosAtualizados.getCanal().getId());
            rotaExistente.setCanal(c);
        }

        return rotaRepository.save(rotaExistente);
    }

    @Transactional
    public void excluir(Long id) {
        if (!rotaRepository.existsById(id)) {
            throw new RuntimeException("Rota não encontrada com id: " + id);
        }
        rotaRepository.deleteById(id);
    }
}