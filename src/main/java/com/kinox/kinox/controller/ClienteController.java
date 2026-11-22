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
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAllClientes() {
        List<ClienteResponse> clientes = clienteService.findAllClientes().stream()
                .map(ClienteMapper::toClienteResponse)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findClienteById(@PathVariable Long id) {
        return clienteService.findClienteById(id)
                .map(cliente -> ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> saveCliente(@RequestBody ClienteRequest clienteRequest) {
        Cliente savedCliente = clienteService.saveCliente(ClienteMapper.toCliente(clienteRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteMapper.toClienteResponse(savedCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest) {
        Optional<Cliente> optCliente = clienteService.findClienteById(id);

        if (optCliente.isPresent()) {
            Cliente cliente = clienteService.updateCliente(id, ClienteMapper.toCliente(clienteRequest));
            return ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> optCliente = clienteService.findClienteById(id);

        if (optCliente.isPresent()) {
            clienteService.deleteClienteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
