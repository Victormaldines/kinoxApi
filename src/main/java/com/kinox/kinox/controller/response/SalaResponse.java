package com.kinox.kinox.controller.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SalaResponse(
        Long id,
        int numero,
        double precoDoIngresso,
        int qtdAssentos,
        boolean is3d,
        boolean isDisponivel,
        boolean isLegendado,
        boolean isImax
) {}
