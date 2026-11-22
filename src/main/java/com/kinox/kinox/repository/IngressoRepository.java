package com.kinox.kinox.repository;

import com.kinox.kinox.entity.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    List<Ingresso> findByCompraClienteId(Long clienteId);

}
