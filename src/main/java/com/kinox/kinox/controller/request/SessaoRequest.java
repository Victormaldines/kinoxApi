package com.kinox.kinox.controller.request;

import java.time.LocalDate;
import java.util.List;

public record SessaoRequest(
        Long filmeId,
        Long salaId,
        boolean disponibilidade,
        LocalDate data,
        int horarioInicio,
        List<Long> sessaoAssentosId
) {}
