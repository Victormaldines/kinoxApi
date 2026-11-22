package com.kinox.kinox.service;

import com.kinox.kinox.entity.*;
import com.kinox.kinox.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    private final SessaoAssentoService sessaoAssentoService;

    @Transactional
    public Sessao saveSessao(Sessao sessao) {
        sessao.setFilme(findFilmes(sessao.getFilme()));
        sessao.setSala(findSalas(sessao.getSala()));

        int quantidadeAssentos = sessao.getSala().getQtdAssentos();
        List<SessaoAssento> sessaoAssentos = populateSessaoAssentos(quantidadeAssentos, sessao);

        sessao.setSessoesAssentos(sessaoAssentos);

        return sessaoRepository.save(sessao);
    }

    public Optional<Sessao> findSessaoById(Long id) {
        return sessaoRepository.findById(id);
    }

    public void deleteSessaoById(Long id) {
        sessaoRepository.deleteById(id);
    }

    private List<SessaoAssento> populateSessaoAssentos(int quantidadeAssentos, Sessao sessao) {
        List<SessaoAssento> sessaoAssentos = new ArrayList<>();

        for(int i = 1; i <= quantidadeAssentos; i++) {
            Assento assento = assentoRepository.save(
                    Assento.builder().numero(i).build()
            );

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
        return filmeRepository.findById(filme.getId()).get();
    }

    private Sala findSalas(Sala sala) {return salaRepository.findById(sala.getId()).get();}
}
