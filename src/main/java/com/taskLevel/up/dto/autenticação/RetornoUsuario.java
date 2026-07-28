package com.taskLevel.up.dto.autenticação;

public record RetornoUsuario(String token,
                             String tokenType,
                             String nomeUsuario,
                             Long tokenTime) {

    public static RetornoUsuario bearer(String token, String nomeUsuario, Long tokenTime) {
        return new RetornoUsuario(token, "Bearer", nomeUsuario, tokenTime);
    }
}
