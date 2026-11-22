package com.kinox.kinox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Sessao sessao;

    @Column(name = "is_inteira")
    boolean isInteira;

}
