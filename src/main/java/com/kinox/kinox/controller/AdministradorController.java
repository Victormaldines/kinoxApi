package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.AdministradorRequest;
import com.kinox.kinox.controller.response.AdministradorResponse;
import com.kinox.kinox.entity.Administrador;
import com.kinox.kinox.mapper.AdministradorMapper;
import com.kinox.kinox.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @GetMapping
    public ResponseEntity<List<AdministradorResponse>> findAll() {
        List<AdministradorResponse> admins = administradorService.findAll().stream()
                .map(AdministradorMapper::toAdministradorResponse)
                .toList();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponse> findById(@PathVariable Long id) {
        return administradorService.findAdministradorById(id)
                .map(admin -> ResponseEntity.ok(AdministradorMapper.toAdministradorResponse(admin)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdministradorResponse> save(@RequestBody AdministradorRequest request) {
        Administrador saved = administradorService.saveAdministrador(AdministradorMapper.toAdministrador(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AdministradorMapper.toAdministradorResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (administradorService.findAdministradorById(id).isPresent()) {
            administradorService.deleteAdministradorById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}