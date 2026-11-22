package com.kinox.kinox.service;

import com.kinox.kinox.entity.Assento;
import com.kinox.kinox.repository.AssentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssentoService {

    private final AssentoRepository assentoRepository;

    public Optional<Assento> findAssentoById(Long id) {
        return assentoRepository.findById(id);
    }

    public Assento saveAssento(Assento assento) {
        return assentoRepository.save(assento);
    }

    public void deleteAssentoById(Long id) {
        assentoRepository.deleteById(id);
    }

}
