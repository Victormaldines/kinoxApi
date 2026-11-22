package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(name = "met_pagamento")
    private String metPagamento;

    @Column(nullable = false)
    private String nome;

    private String nascimento;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String email;
}