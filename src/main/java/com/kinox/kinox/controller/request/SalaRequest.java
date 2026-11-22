package com.kinox.kinox.controller.request;

import java.util.List;

public record SalaRequest(
        int numero,
        double precoDoIngresso,
        int qtdAssentos,
        boolean is3d,
        boolean isDisponivel,
        boolean isLegendado,
        boolean isImax
) {}
