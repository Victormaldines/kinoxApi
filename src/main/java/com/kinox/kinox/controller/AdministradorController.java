package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.AdministradorRequest;
import com.kinox.kinox.controller.response.AdministradorResponse;
import com.kinox.kinox.entity.Administrador;
import com.kinox.kinox.mapper.AdministradorMapper;
import com.kinox.kinox.service.AdministradorService;
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
@RequestMapping("/administradores")
@RequiredArgsConstructor
@Tag(name = "Administradores", description = "Gerenciamento exclusivo de administradores do cinema")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class AdministradorController {

    private final AdministradorService administradorService;

    @GetMapping
    @Operation(summary = "Listar administradores", description = "Exclusivo ADMIN.")
    public ResponseEntity<List<AdministradorResponse>> findAllAdministradores() {
        List<AdministradorResponse> admins = administradorService.findAllAdministradores().stream()
                .map(AdministradorMapper::toAdministradorResponse)
                .toList();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar administrador por ID", description = "Exclusivo ADMIN.")
    public ResponseEntity<AdministradorResponse> findAdministradorById(@PathVariable Long id) {
        return administradorService.findAdministradorById(id)
                .map(admin -> ResponseEntity.ok(AdministradorMapper.toAdministradorResponse(admin)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar administrador", description = "Exclusivo ADMIN.")
    public ResponseEntity<AdministradorResponse> saveAdministrador(@RequestBody AdministradorRequest request) {
        Administrador saved = administradorService.saveAdministrador(AdministradorMapper.toAdministrador(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AdministradorMapper.toAdministradorResponse(saved));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover administrador", description = "Exclusivo ADMIN.")
    public ResponseEntity<Void> deleteAdministrador(@PathVariable Long id) {
        Optional<Administrador> optAdmin = administradorService.findAdministradorById(id);

        if (optAdmin.isPresent()) {
            administradorService.deleteAdministradorById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
