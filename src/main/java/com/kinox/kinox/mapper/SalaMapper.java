package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.SalaRequest;
import com.kinox.kinox.controller.response.SalaResponse;
import com.kinox.kinox.entity.Sala;

public class SalaMapper {

    public static Sala toSala(SalaRequest salaRequest) {
        return Sala.builder()
                .numero(salaRequest.numero())
                .precoDoIngresso(salaRequest.precoDoIngresso())
                .qtdAssentos(salaRequest.qtdAssentos())
                .is3d(salaRequest.is3d())
                .isDisponivel(salaRequest.isDisponivel())
                .isImax(salaRequest.isImax())
                .build();
    }

    public static SalaResponse toSalaResponse(Sala sala) {
        return SalaResponse.builder()
                .id(sala.getId())
                .numero(sala.getNumero())
                .precoDoIngresso(sala.getPrecoDoIngresso())
                .qtdAssentos(sala.getQtdAssentos())
                .is3d(sala.is3d())
                .isDisponivel(sala.isDisponivel())
                .isImax(sala.isImax())
                .build();
    }

}
