package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.SessaoRequest;
import com.kinox.kinox.controller.response.FilmeResponse;
import com.kinox.kinox.controller.response.SalaResponse;
import com.kinox.kinox.controller.response.SessaoAssentoResponse;
import com.kinox.kinox.controller.response.SessaoResponse;
import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.entity.Sala;
import com.kinox.kinox.entity.Sessao;

import java.util.List;

public class SessaoMapper {

    public static Sessao toSessao(SessaoRequest sessaoRequest) {
        Filme filme = Filme.builder().id(sessaoRequest.filmeId()).build();
        Sala sala = Sala.builder().id(sessaoRequest.salaId()).build();

        return Sessao.builder()
                .disponibilidade(sessaoRequest.disponibilidade())
                .data(sessaoRequest.data())
                .horarioInicio(sessaoRequest.horarioInicio())
                .isLegendado(sessaoRequest.isLegendado())
                .filme(filme)
                .sala(sala)
                .build();
    }

    public static SessaoResponse toSessaoResponse(Sessao sessao) {
        FilmeResponse filmeResponse = FilmeMapper.toFilmeResponse(sessao.getFilme());
        SalaResponse salaResponse = SalaMapper.toSalaResponse(sessao.getSala());
        List<SessaoAssentoResponse> sessaoAssentoResponses = sessao.getSessoesAssentos() != null
                ? sessao.getSessoesAssentos().stream()
                    .map(SessaoAssentoMapper::toSessaoAssentoResponse)
                    .toList()
                : List.of();

        return SessaoResponse.builder()
                .id(sessao.getId())
                .disponibilidade(sessao.isDisponibilidade())
                .data(sessao.getData())
                .horarioInicio(sessao.getHorarioInicio())
                .isLegendado(sessao.isLegendado())
                .filme(filmeResponse)
                .sala(salaResponse)
                .sessaoAssentos(sessaoAssentoResponses)
                .build();
    }

}
