package com.kinox.kinox.service;

import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.repository.FilmeRepository;
import com.kinox.kinox.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmesRepository;
    private final GeneroRepository generoRepository;

    public List<Filme> findAllFilmes() {
        return filmesRepository.findAll();
    }

    public Filme saveFilme(Filme filme) {
        filme.setGeneros(findGeneros(filme.getGeneros()));

        return filmesRepository.save(filme);
    }

    private List<Genero> findGeneros(List<Genero> generos) {
        return generos.stream()
                .map(genero -> generoRepository.findById(genero.getId()).get())
                .toList();
    }
}
