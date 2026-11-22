package com.kinox.kinox.controller.response;

import com.kinox.kinox.entity.Genero;
import lombok.Builder;

import java.util.List;

@Builder
public record FilmeResponse(
        Long id,
        int duracao,
        String sinopse,
        String nome,
        int classificacaoIndicativa,
        String diretor,
        String imagemUrl,
        List<GeneroResponse> generos
) {}
