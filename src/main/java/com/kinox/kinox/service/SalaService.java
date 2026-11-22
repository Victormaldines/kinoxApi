package com.kinox.kinox.service;

import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.entity.Sala;
import com.kinox.kinox.repository.SalaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Transactional
    public Sala updateSala(Long id, Sala salaAtualizada) {
        Sala salaExistente = salaRepository.findById(id).orElseThrow();
        salaExistente.setNumero(salaAtualizada.getNumero());
        salaExistente.setPrecoDoIngresso(salaAtualizada.getPrecoDoIngresso());
        salaExistente.setQtdAssentos(salaAtualizada.getQtdAssentos());
        salaExistente.set3d(salaAtualizada.is3d());
        salaExistente.setDisponivel(salaAtualizada.isDisponivel());
        salaExistente.setImax(salaAtualizada.isImax());
        return salaRepository.save(salaExistente);
    }

    @Transactional
    public Sala saveSala(Sala sala) {
        List<Assento> assentos = new ArrayList<>();
        for (int i = 1; i <= sala.getQtdAssentos(); i++) {
            assentos.add(Assento.builder()
                    .numero(i)
                    .sala(sala)
                    .build());
        }
        sala.setAssentos(assentos);
        return salaRepository.save(sala);
    }

}
