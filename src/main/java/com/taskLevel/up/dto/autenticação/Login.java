package com.taskLevel.up.dto.autenticação;

import jakarta.validation.constraints.NotBlank;

public record Login(@NotBlank(message = "Nome inválido!")
                    String nomeUsuario,
                    @NotBlank(message = "Senha inválida!")
                    String senha){
}
