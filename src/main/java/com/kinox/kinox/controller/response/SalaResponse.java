package com.kinox.kinox.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SalaResponse(
        Long id,
        int numero,
        BigDecimal precoDoIngresso,
        int qtdAssentos,
        boolean is3d,
        boolean isDisponivel,
        boolean isImax
) {}
