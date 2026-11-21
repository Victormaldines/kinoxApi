package com.kinox.kinox.controller;

import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    @GetMapping // Rota1
    public List<Genero> findAllGeneros() {
        return generoService.findAllGeneros();
    }

    @PostMapping
    public Genero saveGenero(@RequestBody Genero genero) {
        return generoService.saveGenero(genero);
    }

    @DeleteMapping("/{id}")
    public void deleteGenero(@PathVariable Long id) {
        generoService.deleteGeneroById(id);
    }

}
