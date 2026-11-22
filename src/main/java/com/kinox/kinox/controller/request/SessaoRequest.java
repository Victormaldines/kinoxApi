package com.kinox.kinox.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessaoRequest(
        Long filmeId,
        Long salaId,
        boolean disponibilidade,
        LocalDate data,
        LocalTime horarioInicio,
        boolean isLegendado
) {}
