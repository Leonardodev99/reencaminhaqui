package com.clafaleo.reencaminhaqui.repositories;

import com.clafaleo.reencaminhaqui.entities.Anexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    List<Anexo> findByMensagemId(Long mensagemId);
    Page<Anexo> findByMensagemId(Long mensagemId, Pageable pageable);
}