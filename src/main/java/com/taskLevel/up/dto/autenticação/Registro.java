package com.taskLevel.up.dto.autenticação;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Registro(
        @NotBlank(message = "Nome obrigatório!")
        @Size(min = 2, message = "Nome precisa ser maior!")
        @Size(max = 20, message = "Nome muito grande!")
        String nomeUsuario,

        @NotBlank(message = "Senha obrigatório!")
        @Size(min = 2, message = "Senha precisa ser maior!")
        @Size(max = 20, message = "Senha muito grande!")
        String senha,

        @NotBlank(message = "Email obrigatório!")
        @Email(message = "Email inválido!")
        String email
) {
}
