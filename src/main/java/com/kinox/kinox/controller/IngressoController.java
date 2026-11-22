package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.IngressoRequest;
import com.kinox.kinox.controller.response.IngressoResponse;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.mapper.IngressoMapper;
import com.kinox.kinox.service.IngressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService ingressoService;

    @GetMapping
    public ResponseEntity<List<IngressoResponse>> findAll() {
        List<IngressoResponse> ingressos = ingressoService.findAll().stream()
                .map(IngressoMapper::toIngressoResponse)
                .toList();
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoResponse> findById(@PathVariable Long id) {
        return ingressoService.findIngressoById(id)
                .map(ingresso -> ResponseEntity.ok(IngressoMapper.toIngressoResponse(ingresso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IngressoResponse> save(@RequestBody IngressoRequest request) {
        Ingresso saved = ingressoService.saveIngresso(IngressoMapper.toIngresso(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(IngressoMapper.toIngressoResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (ingressoService.findIngressoById(id).isPresent()) {
            ingressoService.deleteIngressoById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}