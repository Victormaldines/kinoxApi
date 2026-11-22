package com.kinox.kinox.controller.response;

import lombok.Builder;

@Builder
public record SessaoAssentoResponse (Long id, AssentoResponse assento, boolean isOcupado) {}
