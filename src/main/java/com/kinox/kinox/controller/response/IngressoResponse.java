package com.kinox.kinox.controller.response;

import lombok.Builder;

@Builder
public record IngressoResponse(
        Long id,
        Long sessaoId,
        boolean inteira
) {}
