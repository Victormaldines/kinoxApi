package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.CompraRequest;
import com.kinox.kinox.controller.response.CompraResponse;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.mapper.CompraMapper;
import com.kinox.kinox.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraResponse>> findAll() {
        List<CompraResponse> compras = compraService.findAll().stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CompraResponse>> findByClienteId(@PathVariable Long clienteId) {
        List<CompraResponse> compras = compraService.findByClienteId(clienteId).stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> findById(@PathVariable Long id) {
        return compraService.findCompraById(id)
                .map(compra -> ResponseEntity.ok(CompraMapper.toCompraResponse(compra)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CompraResponse> save(@RequestBody CompraRequest request) {
        Compra saved = compraService.saveCompra(CompraMapper.toCompra(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CompraMapper.toCompraResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (compraService.findCompraById(id).isPresent()) {
            compraService.deleteCompraById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}