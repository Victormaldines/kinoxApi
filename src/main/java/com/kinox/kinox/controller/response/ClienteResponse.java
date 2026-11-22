package com.kinox.kinox.controller.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClienteResponse(
        Long id,
        String cpf,
        String nome,
        LocalDate nascimento,
        String email
) {}
