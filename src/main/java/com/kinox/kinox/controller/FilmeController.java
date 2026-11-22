package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.FilmeRequest;
import com.kinox.kinox.controller.response.FilmeResponse;
import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.mapper.FilmeMapper;
import com.kinox.kinox.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
@RequiredArgsConstructor
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping
    public ResponseEntity<List<FilmeResponse>> findAllFilmes() {
        List<Filme> filmes = filmeService.findAllFilmes();
        List<FilmeResponse> filmeResponses = filmes.stream()
                .map(FilmeMapper::toFilmeResponse)
                .toList();

        return ResponseEntity.ok(filmeResponses);
    }

    @PostMapping
    public ResponseEntity<FilmeResponse> saveFilme(@RequestBody FilmeRequest filmeRequest) {
        Filme filme = FilmeMapper.toFilme(filmeRequest);
        Filme savedFilme = filmeService.saveFilme(filme);
        FilmeResponse filmeResponse = FilmeMapper.toFilmeResponse(savedFilme);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(filmeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeResponse> updateFilme(@PathVariable Long id, @RequestBody FilmeRequest filmeRequest) {
        Optional<Filme> optFilme = filmeService.findFilmeById(id);

        if (optFilme.isPresent()) {
            Filme filme = filmeService.updateFilme(id, FilmeMapper.toFilme(filmeRequest));
            return ResponseEntity.ok(FilmeMapper.toFilmeResponse(filme));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmeById(@PathVariable Long id) {
        Optional<Filme> optFilme = filmeService.findFilmeById(id);

        if (optFilme.isPresent()) {
            filmeService.deleteFilmeById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
