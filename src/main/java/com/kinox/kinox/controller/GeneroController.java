package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.GeneroRequest;
import com.kinox.kinox.controller.response.GeneroResponse;
import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.mapper.GeneroMapper;
import com.kinox.kinox.service.GeneroService;
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
@RequiredArgsConstructor
@RequestMapping("/generos")
@Tag(name = "Gêneros", description = "Consulta pública e gerenciamento administrativo de gêneros")
public class GeneroController {

    private final GeneroService generoService;

    @GetMapping
    @Operation(summary = "Listar gêneros", description = "Rota pública que lista todos os gêneros.")
    public ResponseEntity<List<GeneroResponse>> findAllGeneros() {
        List<Genero> generos = generoService.findAllGeneros();

        List<GeneroResponse> generoResponses = generos.stream()
                .map(GeneroMapper::toGeneroResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(generoResponses);
    }

    @PostMapping
    @Operation(summary = "Cadastrar gênero", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<GeneroResponse> saveGenero(@RequestBody GeneroRequest generoRequest) {
        Genero genero = GeneroMapper.toGenero(generoRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GeneroMapper.toGeneroResponse(generoService.saveGenero(genero)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover gênero", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        Optional<Genero> optGenero = generoService.findGeneroById(id);

        if (optGenero.isPresent()) {
            generoService.deleteGeneroById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
