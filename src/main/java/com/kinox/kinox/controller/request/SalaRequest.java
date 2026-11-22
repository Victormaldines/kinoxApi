package com.kinox.kinox.controller.request;

import java.math.BigDecimal;

public record SalaRequest(
        int numero,
        BigDecimal precoDoIngresso,
        int qtdAssentos,
        boolean is3d,
        boolean isDisponivel,
        boolean isImax
) {}
