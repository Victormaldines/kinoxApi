package com.kinox.kinox.service;

import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.repository.FilmeRepository;
import com.kinox.kinox.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final GeneroRepository generoRepository;

    public List<Filme> findAllFilmes() {
        return filmeRepository.findAll();
    }

    public Optional<Filme> findFilmeById(Long id) {
        return filmeRepository.findById(id);
    }

    public Filme saveFilme(Filme filme) {
        filme.setGeneros(findGeneros(filme.getGeneros()));

        return filmeRepository.save(filme);
    }

    public Filme updateFilme(Long id, Filme filme) {
        filme.setId(id);
        return filmeRepository.save(filme);
    }

    public void deleteFilmeById(Long id) {
        filmeRepository.deleteById(id);
    }

    private List<Genero> findGeneros(List<Genero> generos) {
        return generos.stream()
                .map(genero -> generoRepository.findById(genero.getId()).get())
                .toList();
    }
}
