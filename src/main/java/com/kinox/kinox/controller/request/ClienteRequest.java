package com.kinox.kinox.controller.request;

import java.time.LocalDate;

public record ClienteRequest(
        String cpf,
        String nome,
        LocalDate nascimento,
        String senha,
        String email
) {}
