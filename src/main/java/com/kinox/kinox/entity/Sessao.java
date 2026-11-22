package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "sessao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean disponibilidade = true;

    private LocalDate data;

    @Column(name = "horario_inicio")
    private LocalTime horarioInicio;

    @Column(name = "is_legendado", nullable = false)
    private boolean isLegendado = false;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @OneToMany(
            mappedBy = "sessao",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SessaoAssento> sessoesAssentos;
}
