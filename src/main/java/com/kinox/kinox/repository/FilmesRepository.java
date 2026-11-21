package com.kinox.kinox.repository;

import com.kinox.kinox.entity.Filmes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmesRepository extends JpaRepository<Filmes, Long> {

}
