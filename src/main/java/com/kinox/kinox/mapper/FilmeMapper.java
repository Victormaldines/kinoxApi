package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.FilmeRequest;
import com.kinox.kinox.controller.response.FilmeResponse;
import com.kinox.kinox.controller.response.GeneroResponse;
import com.kinox.kinox.entity.Filme;
import com.kinox.kinox.entity.Genero;
import com.kinox.kinox.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FilmeMapper {

    private final GeneroRepository generoRepository;

    public static Filme toFilme(FilmeRequest filmeRequest) {
        List<Genero> generos = filmeRequest.generosId().stream()
                .map(generoId -> Genero.builder().id(generoId).build())
                .toList();

        return Filme.builder()
                .sinopse(filmeRequest.sinopse())
                .duracao(filmeRequest.duracao())
                .nome(filmeRequest.nome())
                .classificacaoIndicativa(filmeRequest.classificacaoIndicativa())
                .diretor(filmeRequest.diretor())
                .imagemUrl(filmeRequest.imagemUrl())
                .generos(generos)
                .build();
    }

    public static FilmeResponse toFilmeResponse(Filme filme) {
        List<GeneroResponse> generoResponses = filme.getGeneros().stream()
                .map(GeneroMapper::toGeneroResponse)
                .toList();

        return FilmeResponse.builder()
                .id(filme.getId())
                .duracao(filme.getDuracao())
                .sinopse(filme.getSinopse())
                .nome(filme.getNome())
                .classificacaoIndicativa(filme.getClassificacaoIndicativa())
                .diretor(filme.getDiretor())
                .imagemUrl(filme.getImagemUrl())
                .generos(generoResponses)
                .build();
    }

}
