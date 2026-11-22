package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.GeneroRequest;
import com.kinox.kinox.controller.response.GeneroResponse;
import com.kinox.kinox.entity.Genero;

public class GeneroMapper {

    public static Genero toGenero(GeneroRequest generoRequest) {
        return Genero.builder()
                .nome(generoRequest.nome())
                .build();
    }

    public static GeneroResponse toGeneroResponse(Genero genero) {
        return GeneroResponse.builder()
                .id(genero.getId())
                .nome(genero.getNome())
                .build();
    }

}
