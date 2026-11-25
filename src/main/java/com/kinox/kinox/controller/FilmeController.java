package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.FilmeRequest;
import com.kinox.kinox.controller.response.FilmeResponse;
import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.mapper.FilmeMapper;
import com.kinox.kinox.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
@RequiredArgsConstructor
@Tag(name = "Filmes", description = "Consulta pública e gerenciamento administrativo de filmes")
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping
    @Operation(summary = "Listar filmes", description = "Rota pública que retorna todos os filmes cadastrados.")
    public ResponseEntity<List<FilmeResponse>> findAllFilmes() {
        List<Filme> filmes = filmeService.findAllFilmes();
        List<FilmeResponse> filmeResponses = filmes.stream()
                .map(FilmeMapper::toFilmeResponse)
                .toList();

        return ResponseEntity.ok(filmeResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID", description = "Rota pública que retorna os detalhes de um filme.")
    public ResponseEntity<FilmeResponse> findFilmeById(@PathVariable Long id) {
        return filmeService.findFilmeById(id)
                .map(filme -> ResponseEntity.ok(FilmeMapper.toFilmeResponse(filme)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar filme", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<FilmeResponse> saveFilme(@RequestBody FilmeRequest filmeRequest) {
        Filme filme = FilmeMapper.toFilme(filmeRequest);
        Filme savedFilme = filmeService.saveFilme(filme);
        FilmeResponse filmeResponse = FilmeMapper.toFilmeResponse(savedFilme);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(filmeResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<FilmeResponse> updateFilme(@PathVariable Long id, @RequestBody FilmeRequest filmeRequest) {
        Optional<Filme> optFilme = filmeService.findFilmeById(id);

        if (optFilme.isPresent()) {
            Filme filme = filmeService.updateFilme(id, FilmeMapper.toFilme(filmeRequest));
            return ResponseEntity.ok(FilmeMapper.toFilmeResponse(filme));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover filme", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<Void> deleteFilmeById(@PathVariable Long id) {
        Optional<Filme> optFilme = filmeService.findFilmeById(id);

        if (optFilme.isPresent()) {
            filmeService.deleteFilmeById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
