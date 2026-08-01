package com.taskLevel.up.dto.autenticação;

public record UsuarioToken(String token,
                           String tokenType,
                           String nomeUsuario,
                           Long tokenTime) {

    public static UsuarioToken bearer(String token, String nomeUsuario, Long tokenTime) {
        return new UsuarioToken(token, "Bearer", nomeUsuario, tokenTime);
    }
}
