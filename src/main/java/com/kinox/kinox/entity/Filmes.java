package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "filmes")
public class Filmes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFilme;

    private int duracao;

    private String sinopse;

    @Column(nullable = false)
    private String nome;

    private int classificacaoIndicativa;

    private String diretor;

    @Column(name = "imagem_url")
    private String imagemUrl;

}
