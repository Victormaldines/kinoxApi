package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Assento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

}
