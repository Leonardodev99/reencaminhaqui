package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Canal;
import  com.clafaleo.reencaminhaqui.exceptions.NegocioException;
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
    private final ProvedorRegistry provedores;

    public CanalService(CanalRepository canalRepository, ProvedorRegistry provedores) {
        this.canalRepository = canalRepository;
        this.provedores = provedores;
    }

    @Transactional
    public Canal salvar(Canal canal) {
        canal.setId(null); // criação nunca reaproveita id vindo do cliente
        validarProvedor(canal.getProvedor());
        if (canalRepository.existsByNome(canal.getNome())) {
            throw NegocioException.conflito("Já existe um canal com o nome: " + canal.getNome());
        }
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
        Canal canal = obter(id);
        if (!canal.getRotas().isEmpty()) {
            throw NegocioException.conflito(
                    "O canal possui rotas associadas. Desative-o (status INATIVO) em vez de excluir.");
        }
        canalRepository.delete(canal);
    }

    @Transactional
    public Canal atualizar(Long id, Canal dados) {
        Canal canal = obter(id);
        aplicarNome(canal, dados.getNome());
        aplicarProvedor(canal, dados.getProvedor());
        canal.setStatusCanal(dados.getStatusCanal());
        return canalRepository.save(canal);
    }

    @Transactional
    public Canal atualizarParcial(Long id, CanalUpdateDTO dto) {
        Canal canal = obter(id);
        if (dto.getNome() != null)       aplicarNome(canal, dto.getNome());
        if (dto.getProvedor() != null)   aplicarProvedor(canal, dto.getProvedor());
        if (dto.getStatusCanal() != null) canal.setStatusCanal(dto.getStatusCanal());
        return canalRepository.save(canal);
    }

    // ---- auxiliares ----

    private Canal obter(Long id) {
        return canalRepository.findById(id)
                .orElseThrow(() -> NegocioException.naoEncontrado("Canal não encontrado com id: " + id));
    }

    private void aplicarNome(Canal canal, String novoNome) {
        canal.setNome(novoNome); // o setter normaliza (trim + maiúsculas)
        if (canalRepository.existsByNomeAndIdNot(canal.getNome(), canal.getId())) {
            throw NegocioException.conflito("Já existe um canal com o nome: " + canal.getNome());
        }
    }

    private void aplicarProvedor(Canal canal, String novoProvedor) {
        canal.setProvedor(novoProvedor);
        validarProvedor(canal.getProvedor());
    }

    private void validarProvedor(String provedor) {
        if (!provedores.existe(provedor)) {
            throw NegocioException.invalido("Provedor não suportado: " + provedor);
        }
    }
}