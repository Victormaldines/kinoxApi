package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.ClienteRequest;
import com.kinox.kinox.controller.request.LoginRequest;
import com.kinox.kinox.controller.response.ClienteResponse;
import com.kinox.kinox.controller.response.LoginResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.mapper.ClienteMapper;
import com.kinox.kinox.security.AuthUserDetails;
import com.kinox.kinox.security.TokenService;
import com.kinox.kinox.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Cadastro público de clientes e login com geração de token JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ClienteService clienteService;

    @PostMapping("/register")
    @Operation(summary = "Cadastrar cliente", description = "Rota pública para registro de novos clientes.")
    public ResponseEntity<ClienteResponse> register(@RequestBody ClienteRequest clienteRequest) {
        Cliente savedCliente = clienteService.saveCliente(ClienteMapper.toCliente(clienteRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteMapper.toClienteResponse(savedCliente));
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica cliente (email) ou administrador (login) e retorna o token JWT.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.senha());

        Authentication authentication = authenticationManager.authenticate(credentials);
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .subject(user.getUsername())
                .role(user.getRole().name())
                .build();

        return ResponseEntity.ok(response);
    }

}
