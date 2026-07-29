package com.taskLevel.up.dto.user;

import java.util.List;

public record UsuarioDTO(
         Long id,
         String nome,
         String email,
         int level,
         int totalProximoLevel,
         int XpNecessario,
         int totalXp,
         int diasOfensivos,
         int maiorOfensiva,
         List<String> conquistas
){
}
