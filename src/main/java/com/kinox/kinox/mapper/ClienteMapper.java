package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.ClienteRequest;
import com.kinox.kinox.controller.response.ClienteResponse;
import com.kinox.kinox.entity.Cliente;

public class ClienteMapper {

    public static Cliente toCliente(ClienteRequest request) {
        return Cliente.builder()
                .cpf(request.cpf())
                .metPagamento(request.metPagamento())
                .nome(request.nome())
                .nascimento(request.nascimento())
                .senha(request.senha())
                .email(request.email())
                .build();
    }

    public static ClienteResponse toClienteResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .cpf(cliente.getCpf())
                .metPagamento(cliente.getMetPagamento())
                .nome(cliente.getNome())
                .nascimento(cliente.getNascimento())
                .email(cliente.getEmail())
                .build();
    }
}