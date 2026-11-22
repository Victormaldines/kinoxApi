package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.SessaoRequest;
import com.kinox.kinox.controller.response.SessaoResponse;
import com.kinox.kinox.entity.Sessao;
import com.kinox.kinox.mapper.SessaoMapper;
import com.kinox.kinox.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService sessaoService;

    @GetMapping
    public ResponseEntity<List<SessaoResponse>> findAllSessoes() {
        List<SessaoResponse> sessoes = sessaoService.findAllSessoes().stream()
                .map(SessaoMapper::toSessaoResponse)
                .toList();
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponse> findSessaoById(@PathVariable Long id) {
        return sessaoService.findSessaoById(id)
                .map(sessao -> ResponseEntity.ok(SessaoMapper.toSessaoResponse(sessao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessaoResponse> saveSessao(@RequestBody SessaoRequest sessaoRequest) {
        Sessao sessao = sessaoService.saveSessao(SessaoMapper.toSessao(sessaoRequest));
        SessaoResponse sessaoResponse = SessaoMapper.toSessaoResponse(sessao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sessaoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessao(@PathVariable Long id) {
        Optional<Sessao> optSessao = sessaoService.findSessaoById(id);

        if (optSessao.isPresent()) {
            sessaoService.deleteSessaoById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
