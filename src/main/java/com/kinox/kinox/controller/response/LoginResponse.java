package com.kinox.kinox.controller.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        Long userId,
        String subject,
        String role
) {}
