package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.CompraRequest;
import com.kinox.kinox.controller.response.CompraResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.mapper.CompraMapper;
import com.kinox.kinox.security.JWTUserData;
import com.kinox.kinox.security.Role;
import com.kinox.kinox.service.CompraService;
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
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraResponse>> findAllCompras() {
        List<CompraResponse> compras = compraService.findAllCompras().stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<CompraResponse>> findMyCompras(Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        List<CompraResponse> compras = compraService.findComprasByClienteId(user.userId()).stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CompraResponse>> findComprasByClienteId(
            @PathVariable Long clienteId,
            Authentication authentication) {
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
    public ResponseEntity<CompraResponse> findCompraById(
            @PathVariable Long id,
            Authentication authentication) {
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
    public ResponseEntity<CompraResponse> saveCompra(
            @RequestBody CompraRequest compraRequest,
            Authentication authentication) {
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
    public ResponseEntity<Void> deleteCompra(
            @PathVariable Long id,
            Authentication authentication) {
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
