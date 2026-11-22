package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.AssentoRequest;
import com.kinox.kinox.controller.response.AssentoResponse;
import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.entity.Sala;

public class AssentoMapper {

    public static Assento toAssento(AssentoRequest assentoRequest) {
        Sala sala = assentoRequest.salaId() != null
                ? Sala.builder().id(assentoRequest.salaId()).build()
                : null;

        return Assento.builder()
                .numero(assentoRequest.numero())
                .sala(sala)
                .build();
    }

    public static AssentoResponse toAssentoResponse(Assento assento) {
        return AssentoResponse.builder()
                .id(assento.getId())
                .numero(assento.getNumero())
                .build();
    }
}
