package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.AssentoRequest;
import com.kinox.kinox.controller.response.AssentoResponse;
import com.kinox.kinox.entity.Assento;

public class AssentoMapper {

    public static Assento toAssento(AssentoRequest assentoRequest) {
        return Assento.builder()
                .numero(assentoRequest.numero())
                .build();
    }

    public static AssentoResponse toAssentoResponse(Assento assento) {
        return AssentoResponse.builder()
                .id(assento.getId())
                .numero(assento.getNumero())
                .build();
    }
}
