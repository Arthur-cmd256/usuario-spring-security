package br.com.fiap.users_spring_security.controllers.exception;

import br.com.fiap.users_spring_security.dtos.ErrorResponseDTO;
import br.com.fiap.users_spring_security.exceptions.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Ocorreu um erro inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(UsuarioNaoEncontradoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Usuario não encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
