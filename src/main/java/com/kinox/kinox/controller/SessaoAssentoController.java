package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.SessaoAssentoStatusRequest;
import com.kinox.kinox.controller.response.SessaoAssentoResponse;
import com.kinox.kinox.mapper.SessaoAssentoMapper;
import com.kinox.kinox.service.SessaoAssentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes_assentos")
@RequiredArgsConstructor
public class SessaoAssentoController {

    private final SessaoAssentoService sessaoAssentoService;

    @PatchMapping("/{id}")
    public ResponseEntity<SessaoAssentoResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody SessaoAssentoStatusRequest request) {

        return sessaoAssentoService.updateStatus(id, request.isOcupado())
                .map(sa -> ResponseEntity.ok(SessaoAssentoMapper.toSessaoAssentoResponse(sa)))
                .orElse(ResponseEntity.notFound().build());
    }



}
