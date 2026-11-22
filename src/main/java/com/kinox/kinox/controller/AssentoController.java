package com.kinox.kinox.controller;

import com.kinox.kinox.controller.request.AssentoRequest;
import com.kinox.kinox.controller.response.AssentoResponse;
import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.mapper.AssentoMapper;
import com.kinox.kinox.service.AssentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/assentos")
@RequiredArgsConstructor
public class AssentoController {

    private final AssentoService assentoService;

    // Rota pra criar assento
    @PostMapping
    public ResponseEntity<AssentoResponse> saveAssento(@RequestBody AssentoRequest assentoRequest) {
        Assento assento = AssentoMapper.toAssento(assentoRequest);
        Assento savedAssento = assentoService.saveAssento(assento);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AssentoMapper.toAssentoResponse(savedAssento));
    }

    // o último pra gente ir comer
    // Rota pra deletar assento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssento(@PathVariable Long id) {
        Optional<Assento> optAssento = assentoService.findAssentoById(id);

        if(optAssento.isPresent()) {
            assentoService.deleteAssentoById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
