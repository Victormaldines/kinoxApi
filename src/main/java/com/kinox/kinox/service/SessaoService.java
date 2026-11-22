package com.kinox.kinox.service;

import com.kinox.kinox.entity.*;
import com.kinox.kinox.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final FilmeRepository filmeRepository;
    private final SalaRepository salaRepository;
    private final AssentoRepository assentoRepository;

    public List<Sessao> findAllSessoes() {
        return sessaoRepository.findAll();
    }

    @Transactional
    public Sessao saveSessao(Sessao sessao) {
        sessao.setFilme(findFilmes(sessao.getFilme()));
        sessao.setSala(findSalas(sessao.getSala()));

        List<SessaoAssento> sessaoAssentos = populateSessaoAssentos(sessao.getSala(), sessao);
        sessao.setSessoesAssentos(sessaoAssentos);

        return sessaoRepository.save(sessao);
    }

    public Optional<Sessao> findSessaoById(Long id) {
        return sessaoRepository.findById(id);
    }

    public void deleteSessaoById(Long id) {
        sessaoRepository.deleteById(id);
    }

    private List<SessaoAssento> populateSessaoAssentos(Sala sala, Sessao sessao) {
        List<Assento> assentosDaSala = assentoRepository.findBySalaId(sala.getId());

        if (assentosDaSala.isEmpty()) {
            assentosDaSala = new ArrayList<>();
            for (int i = 1; i <= sala.getQtdAssentos(); i++) {
                Assento assento = assentoRepository.save(
                        Assento.builder()
                                .numero(i)
                                .sala(sala)
                                .build()
                );
                assentosDaSala.add(assento);
            }
        }

        List<SessaoAssento> sessaoAssentos = new ArrayList<>();
        for (Assento assento : assentosDaSala) {
            SessaoAssento sessaoAssento = SessaoAssento.builder()
                    .sessao(sessao)
                    .assento(assento)
                    .isOcupado(false)
                    .build();
            sessaoAssentos.add(sessaoAssento);
        }

        return sessaoAssentos;
    }

    private Filme findFilmes(Filme filme) {
        return filmeRepository.findById(filme.getId()).orElseThrow();
    }

    private Sala findSalas(Sala sala) {
        return salaRepository.findById(sala.getId()).orElseThrow();
    }
}
