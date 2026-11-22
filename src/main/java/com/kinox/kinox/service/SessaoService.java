package com.kinox.kinox.service;

import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.entity.Sessao;
import com.kinox.kinox.repository.FilmeRepository;
import com.kinox.kinox.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final FilmeRepository filmeRepository;

    public Sessao saveSessao(Sessao sessao) {
        sessao.setFilme(findFilmes(sessao.getFilme()));

        return sessaoRepository.save(sessao);
    }

    public Optional<Sessao> findSessaoById(Long id) {
        return sessaoRepository.findById(id);
    }

    public void deleteSessaoById(Long id) {
        sessaoRepository.deleteById(id);
    }

    private Filme findFilmes(Filme filme) {
        return filmeRepository.findById(filme.getId()).get();
    }
}
