package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.SessaoRequest;
import com.kinox.kinox.controller.response.FilmeResponse;
import com.kinox.kinox.controller.response.SessaoResponse;
import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.entity.Sessao;

public class SessaoMapper {

    public static Sessao toSessao(SessaoRequest sessaoRequest) {
        Filme filme = Filme.builder().id(sessaoRequest.filmeId()).build();

        return Sessao.builder()
                .disponibilidade(sessaoRequest.disponibilidade())
                .data(sessaoRequest.data())
                .horarioInicio(sessaoRequest.horarioInicio())
                .filme(filme)
                .build();
    }

    public static SessaoResponse toSessaoResponse(Sessao sessao) {
        FilmeResponse filmeResponse = FilmeMapper.toFilmeResponse(sessao.getFilme());

        return SessaoResponse.builder()
                .id(sessao.getId())
                .disponibilidade(sessao.isDisponibilidade())
                .data(sessao.getData())
                .horarioInicio(sessao.getHorarioInicio())
                .filme(filmeResponse)
                .build();
    }

}
