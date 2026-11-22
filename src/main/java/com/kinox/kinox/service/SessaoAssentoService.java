package com.kinox.kinox.service;

import com.kinox.kinox.entity.SessaoAssento;
import com.kinox.kinox.repository.SessaoAssentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoAssentoService {

    private final SessaoAssentoRepository sessaoAssentoRepository;

    @Transactional
    public Optional<SessaoAssento> updateStatus(Long id, boolean isOcupado) {
        return sessaoAssentoRepository.findById(id)
                .map(sessaoAssento -> {
                    sessaoAssento.setOcupado(isOcupado);
                    return sessaoAssentoRepository.save(sessaoAssento);
                });
    }

}
