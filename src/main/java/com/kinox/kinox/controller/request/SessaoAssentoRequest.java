package com.kinox.kinox.controller.request;

public record SessaoAssentoRequest (Long sessaoId, Long assentoId, boolean isOcupado) {}
