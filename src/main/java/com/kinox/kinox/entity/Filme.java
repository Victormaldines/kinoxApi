package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "filme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int duracao;

    private String sinopse;

    @Column(nullable = false)
    private String nome;

    @Column(name = "classificacao_indicativa")
    private int classificacaoIndicativa;

    private String diretor;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToMany
    @JoinTable(name = "filme_genero",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos;

    @OneToMany(
        mappedBy = "filme",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Sessao> sessoes;

}
