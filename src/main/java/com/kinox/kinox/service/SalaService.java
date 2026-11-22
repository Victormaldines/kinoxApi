package com.kinox.kinox.service;

import com.kinox.kinox.entity.Sala;
import com.kinox.kinox.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;

    public List<Sala> findAllSalas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> findSalaById(Long id) {
        return salaRepository.findById(id);
    }

    public Sala updateSala(Long id, Sala sala) {
        sala.setId(id);
        return salaRepository.save(sala);
    }

    public Sala saveSala(Sala sala) {
        return salaRepository.save(sala);
    }

}
