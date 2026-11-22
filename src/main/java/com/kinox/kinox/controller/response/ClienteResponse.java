package com.kinox.kinox.controller.response;

import lombok.Builder;

@Builder
public record ClienteResponse(
        Long id,
        String cpf,
        String metPagamento,
        String nome,
        String nascimento,
        String email
) {}