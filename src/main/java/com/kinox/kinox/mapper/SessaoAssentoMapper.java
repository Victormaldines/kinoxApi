package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.SessaoAssentoRequest;
import com.kinox.kinox.controller.response.AssentoResponse;
import com.kinox.kinox.controller.response.SessaoAssentoResponse;
import com.kinox.kinox.controller.response.SessaoResponse;
import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.entity.Sessao;
import com.kinox.kinox.entity.SessaoAssento;

public class SessaoAssentoMapper {

    public static SessaoAssento toSessaoAssento(SessaoAssentoRequest sessaoAssentoRequest) {
        Sessao sessao = Sessao.builder().id(sessaoAssentoRequest.sessaoId()).build();
        Assento assento = Assento.builder().id(sessaoAssentoRequest.assentoId()).build();

        return SessaoAssento.builder()
                .sessao(sessao)
                .assento(assento)
                .isOcupado(sessaoAssentoRequest.isOcupado())
                .build();
    }

    public static SessaoAssentoResponse toSessaoAssentoResponse(SessaoAssento sessaoAssento) {
        AssentoResponse assentoResponse = AssentoMapper.toAssentoResponse(sessaoAssento.getAssento());

        return SessaoAssentoResponse.builder()
                .assento(assentoResponse)
                .isOcupado(sessaoAssento.isOcupado())
                .build();
    }

}
