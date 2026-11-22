package com.kinox.kinox.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record IngressoResponse(
        Long id,
        Long compraId,
        Long sessaoId,
        SessaoAssentoResponse sessaoAssento,
        boolean isInteira,
        BigDecimal valorPago
) {}
