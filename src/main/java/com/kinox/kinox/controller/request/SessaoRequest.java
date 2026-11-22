package com.kinox.kinox.controller.request;

import java.time.LocalDate;

public record SessaoRequest(Long filmeId, boolean disponibilidade, LocalDate data, int horarioInicio) {}
