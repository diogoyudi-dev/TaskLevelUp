package com.taskLevel.up.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroDTO(
        LocalDateTime tempo,
        int status,
        String erro,
        String mensagem,
        String caminho,
        List<String> detalhes
) {
    public static ErroDTO erroDTO(int status, String erro, String mensagem, String caminho) {
        return new ErroDTO(LocalDateTime.now(), status, erro, mensagem, caminho, null);
    }

    public static ErroDTO erroDTO(int status, String erro, String mensagem, String caminho, List<String> detalhes) {
        return new ErroDTO(LocalDateTime.now(), status, erro, mensagem, caminho, detalhes);
    }
}

