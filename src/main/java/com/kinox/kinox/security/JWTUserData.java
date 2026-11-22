package com.kinox.kinox.security;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long userId,
        String username,
        Role role
) {}
