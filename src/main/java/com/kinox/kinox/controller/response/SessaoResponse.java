package com.kinox.kinox.controller.response;

import com.kinox.kinox.entity.Filme;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SessaoResponse (
        Long id,
        boolean disponibilidade,
        LocalDate data,
        int horarioInicio,
        FilmeResponse filme
) {}
