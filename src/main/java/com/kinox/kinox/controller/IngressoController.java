package com.kinox.kinox.controller;

import com.kinox.kinox.controller.response.IngressoResponse;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.mapper.IngressoMapper;
import com.kinox.kinox.service.IngressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingressos")
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService ingressoService;

    @GetMapping
    public ResponseEntity<List<IngressoResponse>> findAllIngressos() {
        List<IngressoResponse> ingressos = ingressoService.findAllIngressos().stream()
                .map(IngressoMapper::toIngressoResponse)
                .toList();
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoResponse> findIngressoById(@PathVariable Long id) {
        return ingressoService.findIngressoById(id)
                .map(ingresso -> ResponseEntity.ok(IngressoMapper.toIngressoResponse(ingresso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngresso(@PathVariable Long id) {
        Optional<Ingresso> optIngresso = ingressoService.findIngressoById(id);

        if (optIngresso.isPresent()) {
            ingressoService.deleteIngressoById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
