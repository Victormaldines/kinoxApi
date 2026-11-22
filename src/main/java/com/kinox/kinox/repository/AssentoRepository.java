package com.kinox.kinox.repository;

import com.kinox.kinox.entity.Assento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssentoRepository extends JpaRepository<Assento, Long> {

    List<Assento> findBySalaId(Long salaId);

}
