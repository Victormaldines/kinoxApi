package com.kinox.kinox.controller.request;

public record IngressoRequest(
        Long sessaoAssentoId,
        boolean isInteira
) {}
