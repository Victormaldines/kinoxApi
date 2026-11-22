package com.kinox.kinox.repository;

import com.kinox.kinox.entity.SessaoAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessaoAssentoRepository extends JpaRepository<SessaoAssento, Long> {

}
