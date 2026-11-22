package com.kinox.kinox.controller.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record SessaoResponse(
        Long id,
        boolean disponibilidade,
        LocalDate data,
        LocalTime horarioInicio,
        boolean isLegendado,
        FilmeResponse filme,
        SalaResponse sala,
        List<SessaoAssentoResponse> sessaoAssentos
) {}
