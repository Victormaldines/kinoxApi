package com.kinox.kinox.controller;

import com.kinox.kinox.config.OpenApiConfig;
import com.kinox.kinox.controller.request.SessaoRequest;
import com.kinox.kinox.controller.response.SessaoResponse;
import com.kinox.kinox.entity.Sessao;
import com.kinox.kinox.mapper.SessaoMapper;
import com.kinox.kinox.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor
@Tag(name = "Sessões", description = "Consulta pública e gerenciamento administrativo de sessões")
public class SessaoController {

    private final SessaoService sessaoService;

    @GetMapping
    @Operation(summary = "Listar sessões", description = "Rota pública que lista todas as sessões.")
    public ResponseEntity<List<SessaoResponse>> findAllSessoes() {
        List<SessaoResponse> sessoes = sessaoService.findAllSessoes().stream()
                .map(SessaoMapper::toSessaoResponse)
                .toList();
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por ID", description = "Rota pública que retorna a sessão e o mapa de assentos com status de ocupação.")
    public ResponseEntity<SessaoResponse> findSessaoById(@PathVariable Long id) {
        return sessaoService.findSessaoById(id)
                .map(sessao -> ResponseEntity.ok(SessaoMapper.toSessaoResponse(sessao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar sessão", description = "Exclusivo ADMIN. Instancia os registros de ocupação para os assentos da sala.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<SessaoResponse> saveSessao(@RequestBody SessaoRequest sessaoRequest) {
        Sessao sessao = sessaoService.saveSessao(SessaoMapper.toSessao(sessaoRequest));
        SessaoResponse sessaoResponse = SessaoMapper.toSessaoResponse(sessao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sessaoResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover sessão", description = "Exclusivo ADMIN.")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
    public ResponseEntity<Void> deleteSessao(@PathVariable Long id) {
        Optional<Sessao> optSessao = sessaoService.findSessaoById(id);

        if (optSessao.isPresent()) {
            sessaoService.deleteSessaoById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
