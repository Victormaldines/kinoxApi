package com.kinox.kinox.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrador")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

}
