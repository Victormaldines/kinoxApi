package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingresso")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @Column(nullable = false)
    private boolean inteira;
}