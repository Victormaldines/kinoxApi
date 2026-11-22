package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_assento_id", nullable = false, unique = true)
    private SessaoAssento sessaoAssento;

    @Column(name = "is_inteira", nullable = false)
    private boolean isInteira = true;

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

}
