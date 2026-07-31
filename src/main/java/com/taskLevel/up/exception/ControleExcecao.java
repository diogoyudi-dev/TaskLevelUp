package com.taskLevel.up.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControleExcecao {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroDTO> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErroDTO.erroDTO(404, "Not Found", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErroDTO> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErroDTO.erroDTO(404, "Conflict", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroDTO> handleBusinessException(BusinessException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErroDTO.erroDTO(404, "Unauthorized", ex.getMessage(), req.getRequestURI()));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroDTO> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErroDTO.erroDTO(401, "Unauthorized", "Usuário ou senha inválidos", req.getRequestURI()));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroDTO> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErroDTO.erroDTO(403, "Forbidden", "Voce nao tem permissão para acessar este recurso", req.getRequestURI()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErroDTO.erroDTO(400, "Bad Request", "Erro de validação nos dados enviados", req.getRequestURI(), details));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handleGeneric(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErroDTO.erroDTO(500, "Internal Server Error",
                        "Ocorreu um erro inesperado. Tente novamente mais tarde.", req.getRequestURI()));
    }
}
