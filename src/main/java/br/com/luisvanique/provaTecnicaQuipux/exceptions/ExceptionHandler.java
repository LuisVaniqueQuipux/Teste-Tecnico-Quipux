package br.com.luisvanique.provaTecnicaQuipux.exceptions;

import br.com.luisvanique.provaTecnicaQuipux.dtos.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NomeNuloException.class)
    public ResponseEntity<ApiError> NomeNuloExceptionHandle(NomeNuloException ex){
       ApiError apiError = ApiError.builder().code
               (HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.name()).errors(List.of(ex.getMessage())).build();
       return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ListaNaoExisteException.class)
    public ResponseEntity<ApiError> ListaNotFoundExceptionHandle(ListaNaoExisteException ex){
        ApiError apiError = ApiError.builder().code
                (HttpStatus.NOT_FOUND.value()).status(HttpStatus.NOT_FOUND.name()).errors(List.of(ex.getMessage())).build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
