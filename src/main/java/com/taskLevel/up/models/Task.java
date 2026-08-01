package com.taskLevel.up.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Usuario usuario;
    @Column(nullable = false, length = 20)
    private String titulo;
    @Column(nullable = false, length = 150)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Dificuldade dificuldade;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusTask statusTask = StatusTask.PENDENTE;

    private LocalDateTime data;

    private Integer xpGanho;
    @Column(nullable = false)
    @Builder.Default
    private boolean atividadeAtrasadaCompleta =  false;
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime criado = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime completado;


}
