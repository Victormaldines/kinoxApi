package com.kinox.kinox.controller.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CompraResponse(
        Long id,
        ClienteResponse cliente,
        BigDecimal valorTotal,
        String metPagamento,
        LocalDateTime dataCompra,
        List<IngressoResponse> ingressos
) {}
