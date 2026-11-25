package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.CompraRequest;
import com.kinox.kinox.controller.response.CompraResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.mapper.CompraMapper;
import com.kinox.kinox.security.JWTUserData;
import com.kinox.kinox.security.Role;
import com.kinox.kinox.service.CompraService;
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
@RequestMapping("/compras")
@RequiredArgsConstructor
@Tag(name = "Compras", description = "Pedidos de compra de ingressos")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    @Operation(summary = "Listar todas as compras", description = "Exclusivo ADMIN.")
    public ResponseEntity<List<CompraResponse>> findAllCompras() {
        List<CompraResponse> compras = compraService.findAllCompras().stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/minhas")
    @Operation(summary = "Listar minhas compras", description = "Exclusivo CLIENTE. Retorna o histórico de compras do cliente autenticado.")
    public ResponseEntity<List<CompraResponse>> findMyCompras(@Parameter(hidden = true) Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        List<CompraResponse> compras = compraService.findComprasByClienteId(user.userId()).stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar compras por cliente", description = "Permitido para ADMIN ou para o próprio CLIENTE.")
    public ResponseEntity<List<CompraResponse>> findComprasByClienteId(
            @PathVariable Long clienteId,
            @Parameter(hidden = true) Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        if (user.role() == Role.CLIENTE && !user.userId().equals(clienteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<CompraResponse> compras = compraService.findComprasByClienteId(clienteId).stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar compra por ID", description = "Permitido para ADMIN ou para o CLIENTE dono da compra.")
    public ResponseEntity<CompraResponse> findCompraById(
            @PathVariable Long id,
            @Parameter(hidden = true) Authentication authentication) {
        Optional<Compra> optCompra = compraService.findCompraById(id);
        if (optCompra.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Compra compra = optCompra.get();
        if (!hasAccessToCompra(compra, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(CompraMapper.toCompraResponse(compra));
    }

    @PostMapping
    @Operation(summary = "Realizar compra", description = "Cria a compra, ocupa os assentos selecionados e calcula valorPago e valorTotal no backend.")
    public ResponseEntity<CompraResponse> saveCompra(
            @RequestBody CompraRequest compraRequest,
            @Parameter(hidden = true) Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();

        Long clienteId;
        if (user.role() == Role.CLIENTE) {
            if (compraRequest.clienteId() != null && !compraRequest.clienteId().equals(user.userId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            clienteId = user.userId();
        } else {
            if (compraRequest.clienteId() == null) {
                throw new IllegalArgumentException("clienteId é obrigatório para administradores.");
            }
            clienteId = compraRequest.clienteId();
        }

        Compra compra = CompraMapper.toCompra(compraRequest);
        compra.setCliente(Cliente.builder().id(clienteId).build());

        Compra savedCompra = compraService.saveCompra(compra);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CompraMapper.toCompraResponse(savedCompra));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar compra", description = "Permitido para ADMIN ou para o CLIENTE dono da compra. Libera automaticamente os assentos.")
    public ResponseEntity<Void> deleteCompra(
            @PathVariable Long id,
            @Parameter(hidden = true) Authentication authentication) {
        Optional<Compra> optCompra = compraService.findCompraById(id);

        if (optCompra.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!hasAccessToCompra(optCompra.get(), authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        compraService.deleteCompraById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean hasAccessToCompra(Compra compra, Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        return user.role() == Role.ADMIN || compra.getCliente().getId().equals(user.userId());
    }

}
