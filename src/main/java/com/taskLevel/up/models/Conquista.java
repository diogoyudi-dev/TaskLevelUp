package com.taskLevel.up.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Conquistas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conquista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;
    @Column(nullable = false, unique = true, length = 100)
    private String nome;
    @Column(nullable = false, length = 250)
    private String descricao;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ModeloConquista modelo;
    @Column(nullable = false)
    private Integer limite;
}
