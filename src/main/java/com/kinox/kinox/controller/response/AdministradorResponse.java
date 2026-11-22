package com.kinox.kinox.controller.response;

import lombok.Builder;

@Builder
public record AdministradorResponse(
        Long id,
        String login
) {}
