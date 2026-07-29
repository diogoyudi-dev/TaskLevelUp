package com.taskLevel.up.dto.task;

import com.taskLevel.up.models.Dificuldade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record    CriacaoTask(
        @NotBlank(message = "Título obrigatório!")
        @Size(max = 50, message = "Título muito grande!")
        String titulo,
        @Size(max = 150, message = "Descrição muito grande!")
        String descricao,
        @NotNull(message = "Dificuldade é obrigatória!")
        Dificuldade dificuldade,
        @Future(message = "Data inválida!")
        LocalDateTime data) {
}
