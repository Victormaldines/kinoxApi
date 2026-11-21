package com.kinox.kinox.controller;

import com.kinox.kinox.service.FilmesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmes")
public class FilmesController {

    private final FilmesService filmesService;

    public FilmesController(FilmesService filmesService) {
        this.filmesService = filmesService;
    }



}
