package com.kinox.kinox.controller.request;

import java.util.List;

public record CompraRequest(
        Long clienteId,
        String metPagamento,
        List<IngressoRequest> ingressos
) {}
