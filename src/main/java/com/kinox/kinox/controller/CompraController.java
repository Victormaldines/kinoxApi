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

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CompraResponse>> findComprasByClienteId(@PathVariable Long clienteId) {
        List<CompraResponse> compras = compraService.findComprasByClienteId(clienteId).stream()
                .map(CompraMapper::toCompraResponse)
                .toList();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> findCompraById(@PathVariable Long id) {
        return compraService.findCompraById(id)
                .map(compra -> ResponseEntity.ok(CompraMapper.toCompraResponse(compra)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CompraResponse> saveCompra(@RequestBody CompraRequest compraRequest) {
        Compra savedCompra = compraService.saveCompra(CompraMapper.toCompra(compraRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CompraMapper.toCompraResponse(savedCompra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        Optional<Compra> optCompra = compraService.findCompraById(id);

        if (optCompra.isPresent()) {
            compraService.deleteCompraById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
