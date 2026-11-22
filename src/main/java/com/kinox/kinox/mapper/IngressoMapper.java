package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.IngressoRequest;
import com.kinox.kinox.controller.response.IngressoResponse;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.entity.SessaoAssento;

public class IngressoMapper {

    public static Ingresso toIngresso(IngressoRequest ingressoRequest) {
        SessaoAssento sessaoAssento = SessaoAssento.builder()
                .id(ingressoRequest.sessaoAssentoId())
                .build();

        return Ingresso.builder()
                .sessaoAssento(sessaoAssento)
                .isInteira(ingressoRequest.isInteira())
                .build();
    }

    public static IngressoResponse toIngressoResponse(Ingresso ingresso) {
        return IngressoResponse.builder()
                .id(ingresso.getId())
                .compraId(ingresso.getCompra().getId())
                .sessaoId(ingresso.getSessaoAssento().getSessao().getId())
                .sessaoAssento(SessaoAssentoMapper.toSessaoAssentoResponse(ingresso.getSessaoAssento()))
                .isInteira(ingresso.isInteira())
                .valorPago(ingresso.getValorPago())
                .build();
    }

}
