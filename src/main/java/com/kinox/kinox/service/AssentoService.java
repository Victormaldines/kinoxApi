package com.kinox.kinox.service;

import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.entity.Sala;
import com.kinox.kinox.repository.AssentoRepository;
import com.kinox.kinox.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssentoService {

    private final AssentoRepository assentoRepository;
    private final SalaRepository salaRepository;

    public Optional<Assento> findAssentoById(Long id) {
        return assentoRepository.findById(id);
    }

    public Assento saveAssento(Assento assento) {
        if (assento.getSala() != null && assento.getSala().getId() != null) {
            Sala sala = salaRepository.findById(assento.getSala().getId()).orElseThrow();
            assento.setSala(sala);
        }
        return assentoRepository.save(assento);
    }

    public void deleteAssentoById(Long id) {
        assentoRepository.deleteById(id);
    }

}
