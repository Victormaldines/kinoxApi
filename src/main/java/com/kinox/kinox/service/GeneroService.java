package com.kinox.kinox.service;

import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public List<Genero> findAllGeneros() {
        return generoRepository.findAll();
    }

    public Genero saveGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    public void deleteGeneroById(Long id) {
        generoRepository.deleteById(id);
    }

}
