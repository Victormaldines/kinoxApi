package com.kinox.kinox.service;

import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.entity.Sessao;
import com.kinox.kinox.repository.IngressoRepository;
import com.kinox.kinox.repository.SessaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final SessaoRepository sessaoRepository;

    @Transactional
    public Ingresso saveIngresso(Ingresso ingresso) {
        Sessao sessao = sessaoRepository.findById(ingresso.getSessao().getId()).orElseThrow();
        ingresso.setSessao(sessao);
        return ingressoRepository.save(ingresso);
    }

    public List<Ingresso> findAll() {
        return ingressoRepository.findAll();
    }

    public Optional<Ingresso> findIngressoById(Long id) {
        return ingressoRepository.findById(id);
    }

    public void deleteIngressoById(Long id) {
        ingressoRepository.deleteById(id);
    }
}