package com.clafaleo.reencaminhaqui.repositories;

import com.clafaleo.reencaminhaqui.entities.Canal;
import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {
    List<Canal> findByStatusCanal(StatusCanal statusCanal);
    Optional<Canal> findByNome(String nome);
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, Long id);
}