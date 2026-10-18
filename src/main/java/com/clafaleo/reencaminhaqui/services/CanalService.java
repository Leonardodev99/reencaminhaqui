package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Canal;
import com.clafaleo.reencaminhaqui.entities.dto.CanalUpdateDTO;
import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;
import com.clafaleo.reencaminhaqui.repositories.CanalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CanalService {

    private final CanalRepository canalRepository;


    public CanalService(CanalRepository canalRepository) {
        this.canalRepository = canalRepository;
    }

    @Transactional
    public Canal salvar(Canal canal) {
        return canalRepository.save(canal);
    }

    @Transactional(readOnly = true)
    public List<Canal> listarTodos() {
        return canalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Canal> buscarPorId(Long id) {
        return canalRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Canal> buscarPorStatus(StatusCanal status) {
        return canalRepository.findByStatusCanal(status);
    }

    @Transactional(readOnly = true)
    public Optional<Canal> buscarPorNome(String nome) {
        return canalRepository.findByNome(nome);
    }

    @Transactional
    public void excluir(Long id) {
        canalRepository.deleteById(id);
    }

    @Transactional
    public Canal atualizar(Long id, Canal dadosAtualizados) {
        Canal canalExistente = canalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canal não encontrado com id: " + id));

        canalExistente.setNome(dadosAtualizados.getNome());
        canalExistente.setProvedor(dadosAtualizados.getProvedor());
        canalExistente.setStatusCanal(dadosAtualizados.getStatusCanal());

        return canalRepository.save(canalExistente);
    }

    @Transactional
    public Canal atualizarParcial(Long id, CanalUpdateDTO dto) {
        
        Canal canalExistente = canalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canal não encontrado com id: " + id));

        // Apenas altera os campos que não são nulos no DTO
        if (dto.getNome() != null) {
            canalExistente.setNome(dto.getNome());
        }
        if (dto.getProvedor() != null) {
            canalExistente.setProvedor(dto.getProvedor());
        }
        if (dto.getStatusCanal() != null) {
            canalExistente.setStatusCanal(dto.getStatusCanal());
        }

        return canalRepository.save(canalExistente);
    }
}
