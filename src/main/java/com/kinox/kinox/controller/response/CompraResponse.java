package com.kinox.kinox.controller.response;

import lombok.Builder;

@Builder
public record CompraResponse(
        Long id,
        IngressoResponse ingresso,
        ClienteResponse cliente
) {}
