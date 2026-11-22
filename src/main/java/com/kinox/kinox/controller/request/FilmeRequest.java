package com.kinox.kinox.controller.request;

import java.util.List;

public record FilmeRequest(
        int duracao,
        String sinopse,
        String nome,
        int classificacaoIndicativa,
        String diretor,
        String imagemUrl,
        List<Long> generosId
) {}
