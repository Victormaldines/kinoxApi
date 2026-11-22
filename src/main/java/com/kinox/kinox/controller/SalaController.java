package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.SalaRequest;
import com.kinox.kinox.controller.response.SalaResponse;
import com.kinox.kinox.entity.Sala;
import com.kinox.kinox.mapper.SalaMapper;
import com.kinox.kinox.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponse>> findAllSalas() {
        List<Sala> salas = salaService.findAllSalas();
        List<SalaResponse> salaResponses = salas.stream()
                .map(SalaMapper::toSalaResponse)
                .toList();
        return ResponseEntity.ok(salaResponses);
    }

    @PostMapping
    public ResponseEntity<SalaResponse> saveSala(@RequestBody SalaRequest salaRequest) {
        Sala sala = SalaMapper.toSala(salaRequest);
        Sala savedSala = salaService.saveSala(sala);
        SalaResponse salaResponse = SalaMapper.toSalaResponse(savedSala);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salaResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> updateSala(@PathVariable Long id, @RequestBody SalaRequest salaRequest) {
        Optional<Sala> optSala = salaService.findSalaById(id);

        if (optSala.isPresent()) {
            Sala sala = salaService.updateSala(id, SalaMapper.toSala(salaRequest));
            return ResponseEntity.ok(SalaMapper.toSalaResponse(sala));
        }

        return ResponseEntity.notFound().build();
    }

}
