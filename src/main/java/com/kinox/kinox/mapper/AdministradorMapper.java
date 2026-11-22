package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.AdministradorRequest;
import com.kinox.kinox.controller.response.AdministradorResponse;
import com.kinox.kinox.entity.Administrador;

public class AdministradorMapper {

    public static Administrador toAdministrador(AdministradorRequest request) {
        return Administrador.builder()
                .login(request.login())
                .senha(request.senha())
                .build();
    }

    public static AdministradorResponse toAdministradorResponse(Administrador administrador) {
        return AdministradorResponse.builder()
                .id(administrador.getId())
                .login(administrador.getLogin())
                .build();
    }

}
