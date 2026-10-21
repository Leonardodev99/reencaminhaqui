package com.clafaleo.reencaminhaqui.repositories;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.enums.TipoConteudo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByRotaId(Long rotaId);
    Page<Mensagem> findByRotaId(Long rotaId, Pageable pageable);
    Optional<Mensagem> findByRemetenteOriginal(String remetenteOriginal);
    List<Mensagem> findByTipoConteudo(TipoConteudo tipoConteudo);
    boolean existsByIdExterno(String idExterno);
}