package com.taskLevel.up.dto.ranking;

public record RankDTO(
        int rank,
        String nomeUsuario,
        int level,
        int totalXp
) {
}
