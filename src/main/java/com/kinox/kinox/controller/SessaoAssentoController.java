package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.SessaoAssentoStatusRequest;
import com.kinox.kinox.controller.response.SessaoAssentoResponse;
import com.kinox.kinox.mapper.SessaoAssentoMapper;
import com.kinox.kinox.service.SessaoAssentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes_assentos")
@RequiredArgsConstructor
@Tag(name = "Sessões Assentos", description = "Controle administrativo de ocupação de assentos por sessão")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class SessaoAssentoController {

    private final SessaoAssentoService sessaoAssentoService;

    @PatchMapping("/{id}")
    @Operation(summary = "Alterar ocupação do assento", description = "Exclusivo ADMIN. Atualiza manualmente o status isOcupado de um assento na sessão.")
    public ResponseEntity<SessaoAssentoResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody SessaoAssentoStatusRequest request) {

        return sessaoAssentoService.updateStatus(id, request.isOcupado())
                .map(sa -> ResponseEntity.ok(SessaoAssentoMapper.toSessaoAssentoResponse(sa)))
                .orElse(ResponseEntity.notFound().build());
    }

}
