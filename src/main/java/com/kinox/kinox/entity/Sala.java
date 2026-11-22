package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sala")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @Column(name = "preco_do_ingresso", precision = 10, scale = 2)
    private BigDecimal precoDoIngresso;

    @Column(name = "qtd_assentos")
    private int qtdAssentos;

    @Column(name = "is_3d", nullable = false)
    private boolean is3d = false;

    @Column(name = "is_disponivel", nullable = false)
    private boolean isDisponivel = true;

    @Column(name = "is_imax", nullable = false)
    private boolean isImax = false;

    @OneToMany(
            mappedBy = "sala",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Sessao> sessoes;

    @OneToMany(
            mappedBy = "sala",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Assento> assentos;
}
