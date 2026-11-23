package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.response.IngressoResponse;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.mapper.IngressoMapper;
import com.kinox.kinox.security.JWTUserData;
import com.kinox.kinox.security.Role;
import com.kinox.kinox.service.IngressoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingressos")
@RequiredArgsConstructor
@Tag(name = "Ingressos", description = "Consulta e cancelamento de ingressos emitidos")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class IngressoController {

    private final IngressoService ingressoService;

    @GetMapping
    @Operation(summary = "Listar todos os ingressos", description = "Exclusivo ADMIN.")
    public ResponseEntity<List<IngressoResponse>> findAllIngressos() {
        List<IngressoResponse> ingressos = ingressoService.findAllIngressos().stream()
                .map(IngressoMapper::toIngressoResponse)
                .toList();
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/meus")
    @Operation(summary = "Listar meus ingressos", description = "Exclusivo CLIENTE.")
    public ResponseEntity<List<IngressoResponse>> findMyIngressos(@Parameter(hidden = true) Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        List<IngressoResponse> ingressos = ingressoService.findIngressosByClienteId(user.userId()).stream()
                .map(IngressoMapper::toIngressoResponse)
                .toList();
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ingresso por ID", description = "Permitido para ADMIN ou para o CLIENTE dono do ingresso.")
    public ResponseEntity<IngressoResponse> findIngressoById(
            @PathVariable Long id,
            @Parameter(hidden = true) Authentication authentication) {
        Optional<Ingresso> optIngresso = ingressoService.findIngressoById(id);
        if (optIngresso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Ingresso ingresso = optIngresso.get();
        if (!hasAccessToIngresso(ingresso, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(IngressoMapper.toIngressoResponse(ingresso));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar ingresso", description = "Permitido para ADMIN ou para o CLIENTE dono do ingresso. Libera o assento e abate o valor da compra.")
    public ResponseEntity<Void> deleteIngresso(
            @PathVariable Long id,
            @Parameter(hidden = true) Authentication authentication) {
        Optional<Ingresso> optIngresso = ingressoService.findIngressoById(id);

        if (optIngresso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!hasAccessToIngresso(optIngresso.get(), authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ingressoService.deleteIngressoById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean hasAccessToIngresso(Ingresso ingresso, Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        return user.role() == Role.ADMIN
                || ingresso.getCompra().getCliente().getId().equals(user.userId());
    }

}
