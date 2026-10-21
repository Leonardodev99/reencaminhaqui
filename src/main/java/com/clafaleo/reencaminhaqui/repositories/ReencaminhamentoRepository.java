package com.clafaleo.reencaminhaqui.repositories;

import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.enums.StatusEnvio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReencaminhamentoRepository extends JpaRepository<Reencaminhamento, Long> {
    List<Reencaminhamento> findByMensagemId(Long mensagemId);
    Page<Reencaminhamento> findByMensagemId(Long mensagemId, Pageable pageable);
    List<Reencaminhamento> findByStatusEnvio(StatusEnvio statusEnvio);
    Page<Reencaminhamento> findByStatusEnvio(StatusEnvio statusEnvio, Pageable pageable);

    // novo: usado pelo retry do dispatcher
    List<Reencaminhamento> findByStatusEnvioInAndTentativasLessThan(List<StatusEnvio> status, Integer max);
}