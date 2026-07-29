package com.taskLevel.up.dto.autenticação;

public record UsuarioDTO(String token,
                         String tokenType,
                         String nomeUsuario,
                         Long tokenTime) {

    public static UsuarioDTO bearer(String token, String nomeUsuario, Long tokenTime) {
        return new UsuarioDTO(token, "Bearer", nomeUsuario, tokenTime);
    }
}
