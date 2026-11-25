package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.SalaRequest;
import com.kinox.kinox.controller.response.SalaResponse;
import com.kinox.kinox.entity.Sala;
import com.kinox.kinox.mapper.SalaMapper;
import com.kinox.kinox.service.SalaService;
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
@RequestMapping("/salas")
@RequiredArgsConstructor
@Tag(name = "Salas", description = "Consulta pública e gerenciamento administrativo de salas")
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    @Operation(summary = "Listar salas", description = "Rota pública que lista todas as salas.")
    public ResponseEntity<List<SalaResponse>> findAllSalas() {
        List<Sala> salas = salaService.findAllSalas();
        List<SalaResponse> salaResponses = salas.stream()
                .map(SalaMapper::toSalaResponse)
                .toList();
        return ResponseEntity.ok(salaResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID", description = "Rota pública que retorna os detalhes de uma sala.")
    public ResponseEntity<SalaResponse> findSalaById(@PathVariable Long id) {
        return salaService.findSalaById(id)
                .map(sala -> ResponseEntity.ok(SalaMapper.toSalaResponse(sala)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar sala", description = "Exclusivo ADMIN. Cria a sala e gera automaticamente seus assentos físicos.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<SalaResponse> saveSala(@RequestBody SalaRequest salaRequest) {
        Sala sala = SalaMapper.toSala(salaRequest);
        Sala savedSala = salaService.saveSala(sala);
        SalaResponse salaResponse = SalaMapper.toSalaResponse(savedSala);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salaResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sala", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<SalaResponse> updateSala(@PathVariable Long id, @RequestBody SalaRequest salaRequest) {
        Optional<Sala> optSala = salaService.findSalaById(id);

        if (optSala.isPresent()) {
            Sala sala = salaService.updateSala(id, SalaMapper.toSala(salaRequest));
            return ResponseEntity.ok(SalaMapper.toSalaResponse(sala));
        }

        return ResponseEntity.notFound().build();
    }

}
