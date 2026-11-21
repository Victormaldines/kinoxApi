package com.kinox.kinox.service;

import com.kinox.kinox.repository.FilmesRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmesService {

    private final FilmesRepository filmesRepository;

    public FilmesService(FilmesRepository filmesRepository) {
        this.filmesRepository = filmesRepository;
    }

}
