package com.kinox.kinox.repository;

import com.kinox.kinox.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    //List<Genero> findGeneroByFilmeIdIn(Long id);

}
