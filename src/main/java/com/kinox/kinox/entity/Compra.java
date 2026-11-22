package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingresso_id", nullable = false)
    private Ingresso ingresso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}