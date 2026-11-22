package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.SessaoRequest;
import com.kinox.kinox.controller.response.SessaoResponse;
import com.kinox.kinox.entity.Sessao;
import com.kinox.kinox.mapper.SessaoMapper;
import com.kinox.kinox.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService sessaoService;

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponse> findSessaoById(@PathVariable Long id) {
        System.out.println(sessaoService.findSessaoById(id));
        return sessaoService.findSessaoById(id)
                .map(sessao -> ResponseEntity.ok(SessaoMapper.toSessaoResponse(sessao)))
                .orElse(ResponseEntity.notFound().build());

    }

    // GetSessaoByIsDisponibilidade

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

        if(optSessao.isPresent()) {
            sessaoService.deleteSessaoById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
