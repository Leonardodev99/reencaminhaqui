package com.clafaleo.reencaminhaqui.repositories;

import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.enums.StatusRota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long> {
    List<Rota> findByCanalId(Long canalId);
    Page<Rota> findByCanalId(Long canalId, Pageable pageable);
    List<Rota> findByStatusRota(StatusRota statusRota);
    Optional<Rota> findByNumeroOrigem(String numeroOrigem);
    Optional<Rota> findByNumeroDestino(String numeroDestino);
    Optional<Rota> findByNumeroOrigemAndStatusRota(String numeroOrigem, StatusRota statusRota);
}