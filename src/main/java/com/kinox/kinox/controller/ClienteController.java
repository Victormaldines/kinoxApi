package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.ClienteRequest;
import com.kinox.kinox.controller.response.ClienteResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.mapper.ClienteMapper;
import com.kinox.kinox.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAll() {
        List<ClienteResponse> clientes = clienteService.findAll().stream()
                .map(ClienteMapper::toClienteResponse)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable Long id) {
        return clienteService.findClienteById(id)
                .map(cliente -> ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest request) {
        Cliente saved = clienteService.saveCliente(ClienteMapper.toCliente(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteMapper.toClienteResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (clienteService.findClienteById(id).isPresent()) {
            clienteService.deleteClienteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}