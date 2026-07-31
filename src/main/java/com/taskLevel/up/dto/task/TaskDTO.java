package com.taskLevel.up.dto.task;

import com.taskLevel.up.models.Dificuldade;
import com.taskLevel.up.models.StatusTask;
import com.taskLevel.up.models.Task;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String titulo,
        String descricao,
        Dificuldade dificuldade,
        StatusTask statusTask,
        LocalDateTime data,
        Integer xpGanho,
        boolean atividadeCompleta,
        LocalDate criado,
        LocalDate completado)
{

    public static TaskDTO taskDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitulo(), task.getDescricao(),
                task.getDificuldade(), task.getStatusTask(), task.getData(),
                task.getXpGanho(), task.isAtividadeCompleta(),
                task.getCriado(), task.getCompletado()
        );
    }
}

