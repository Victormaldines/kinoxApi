package com.kinox.kinox.controller.request;

public record ClienteRequest(
        String cpf,
        String metPagamento,
        String nome,
        String nascimento,
        String senha,
        String email
) {}
