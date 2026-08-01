package com.taskLevel.up.dto.task;


import java.util.List;

public record TaskCompleta(TaskDTO taskDTO,
                           int xpGanho,
                           int totalXp,
                           int novoLevel,
                           boolean upouLevel,
                           List<String> conquistasBloqueadas){
}
