package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.IngressoRequest;
import com.kinox.kinox.controller.response.IngressoResponse;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.entity.Sessao;

public class IngressoMapper {

    public static Ingresso toIngresso(IngressoRequest request) {
        Sessao sessao = Sessao.builder().id(request.sessaoId()).build();
        return Ingresso.builder()
                .sessao(sessao)
                .inteira(request.inteira())
                .build();
    }

    public static IngressoResponse toIngressoResponse(Ingresso ingresso) {
        return IngressoResponse.builder()
                .id(ingresso.getId())
                .sessaoId(ingresso.getSessao().getId())
                .inteira(ingresso.isInteira())
                .build();
    }
}