package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.ClienteRequest;
import com.kinox.kinox.controller.response.ClienteResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.mapper.ClienteMapper;
import com.kinox.kinox.security.JWTUserData;
import com.kinox.kinox.security.Role;
import com.kinox.kinox.service.ClienteService;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Perfil do cliente autenticado e gestão de clientes")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Exclusivo ADMIN.")
    public ResponseEntity<List<ClienteResponse>> findAllClientes() {
        List<ClienteResponse> clientes = clienteService.findAllClientes().stream()
                .map(ClienteMapper::toClienteResponse)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/me")
    @Operation(summary = "Consultar próprio perfil", description = "Exclusivo CLIENTE.")
    public ResponseEntity<ClienteResponse> findAuthenticatedCliente(@Parameter(hidden = true) Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        return clienteService.findClienteById(user.userId())
                .map(cliente -> ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    @Operation(summary = "Atualizar próprio perfil", description = "Exclusivo CLIENTE.")
    public ResponseEntity<ClienteResponse> updateAuthenticatedCliente(
            @RequestBody ClienteRequest clienteRequest,
            @Parameter(hidden = true) Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        if (clienteService.findClienteById(user.userId()).isPresent()) {
            Cliente cliente = clienteService.updateCliente(user.userId(), ClienteMapper.toCliente(clienteRequest));
            return ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Permitido para ADMIN ou para o próprio CLIENTE.")
    public ResponseEntity<ClienteResponse> findClienteById(
            @PathVariable Long id,
            @Parameter(hidden = true) Authentication authentication) {
        if (!hasAccessToCliente(id, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return clienteService.findClienteById(id)
                .map(cliente -> ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Exclusivo ADMIN. Para cadastro público utilize POST /auth/register.")
    public ResponseEntity<ClienteResponse> saveCliente(@RequestBody ClienteRequest clienteRequest) {
        Cliente savedCliente = clienteService.saveCliente(ClienteMapper.toCliente(clienteRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteMapper.toClienteResponse(savedCliente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente por ID", description = "Permitido para ADMIN ou para o próprio CLIENTE.")
    public ResponseEntity<ClienteResponse> updateCliente(
            @PathVariable Long id,
            @RequestBody ClienteRequest clienteRequest,
            @Parameter(hidden = true) Authentication authentication) {
        if (!hasAccessToCliente(id, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<Cliente> optCliente = clienteService.findClienteById(id);

        if (optCliente.isPresent()) {
            Cliente cliente = clienteService.updateCliente(id, ClienteMapper.toCliente(clienteRequest));
            return ResponseEntity.ok(ClienteMapper.toClienteResponse(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente por ID", description = "Permitido para ADMIN ou para o próprio CLIENTE.")
    public ResponseEntity<Void> deleteCliente(
            @PathVariable Long id,
            @Parameter(hidden = true) Authentication authentication) {
        if (!hasAccessToCliente(id, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<Cliente> optCliente = clienteService.findClienteById(id);

        if (optCliente.isPresent()) {
            clienteService.deleteClienteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean hasAccessToCliente(Long clienteId, Authentication authentication) {
        JWTUserData user = (JWTUserData) authentication.getPrincipal();
        return user.role() == Role.ADMIN || user.userId().equals(clienteId);
    }

}
