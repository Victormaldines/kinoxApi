package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.AssentoRequest;
import com.kinox.kinox.controller.response.AssentoResponse;
import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.mapper.AssentoMapper;
import com.kinox.kinox.service.AssentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/assentos")
@RequiredArgsConstructor
@Tag(name = "Assentos", description = "Gerenciamento administrativo de assentos físicos")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class AssentoController {

    private final AssentoService assentoService;

    @PostMapping
    @Operation(summary = "Cadastrar assento", description = "Exclusivo ADMIN.")
    public ResponseEntity<AssentoResponse> saveAssento(@RequestBody AssentoRequest assentoRequest) {
        Assento assento = AssentoMapper.toAssento(assentoRequest);
        Assento savedAssento = assentoService.saveAssento(assento);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AssentoMapper.toAssentoResponse(savedAssento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover assento", description = "Exclusivo ADMIN.")
    public ResponseEntity<Void> deleteAssento(@PathVariable Long id) {
        Optional<Assento> optAssento = assentoService.findAssentoById(id);

        if (optAssento.isPresent()) {
            assentoService.deleteAssentoById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
