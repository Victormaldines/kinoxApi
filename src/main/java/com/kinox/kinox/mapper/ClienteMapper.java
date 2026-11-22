package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.ClienteRequest;
import com.kinox.kinox.controller.response.ClienteResponse;
import com.kinox.kinox.entity.Cliente;

public class ClienteMapper {

    public static Cliente toCliente(ClienteRequest clienteRequest) {
        return Cliente.builder()
                .cpf(clienteRequest.cpf())
                .nome(clienteRequest.nome())
                .nascimento(clienteRequest.nascimento())
                .senha(clienteRequest.senha())
                .email(clienteRequest.email())
                .build();
    }

    public static ClienteResponse toClienteResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .nascimento(cliente.getNascimento())
                .email(cliente.getEmail())
                .build();
    }

}
