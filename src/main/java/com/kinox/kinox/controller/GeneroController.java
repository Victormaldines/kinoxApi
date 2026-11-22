package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.GeneroRequest;
import com.kinox.kinox.controller.response.GeneroResponse;
import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.mapper.GeneroMapper;
import com.kinox.kinox.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    @GetMapping // Rota1
    public ResponseEntity<List<GeneroResponse>> findAllGeneros() {
        List<Genero> generos = generoService.findAllGeneros();

        List<GeneroResponse> generoResponses = generos.stream()
                .map(GeneroMapper::toGeneroResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(generoResponses);
    }

    @PostMapping
    public ResponseEntity<GeneroResponse> saveGenero(@RequestBody GeneroRequest generoRequest) {
        Genero genero = GeneroMapper.toGenero(generoRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GeneroMapper.toGeneroResponse(generoService.saveGenero(genero)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        Optional<Genero> optGenero = generoService.findGeneroById(id);

        if(optGenero.isPresent()) {
            generoService.deleteGeneroById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
