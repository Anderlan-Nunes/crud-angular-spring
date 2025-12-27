package com.anderlan.crud_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.anderlan.crud_spring.exception.RecordNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice // eh a classe que vai dizer para todos os rest controllers o que fazer com as excecoes

public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // quando der a excecao RecordNotFoundException, a gente vai retornar o status 404    
   // vamos ter os metodos que vao tratar as excecoes do jeito que a gente quer usando o nosso RecordNotFoundException
    public String handleNotFoundException(RecordNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .reduce("", (acc, error) -> acc + error+ "\n");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException ex){ //aceitei da ia
        return ex.getConstraintViolations().stream()
        .map(violation -> violation.getPropertyPath() + ": " +violation.getMessage())
        .reduce("", (acc, error) -> acc + error + "\n");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        if (ex != null && ex.getRequiredType() != null) {
            String type = ex.getRequiredType().getName();
            String[] typeParts = type.split("\\.");
            String typeName = typeParts[typeParts.length - 1];
            return ex.getName() + ": tipo inválido. Esperado: " + typeName + ", Valor recebido: " + ex.getValue();
        } 
        return "tipo inválido. Valor recebido";
    }

}
    // esse metodo vai pegar todos os erros de validacao e transformar em uma string so, onde cada erro vai estar em uma linha diferente.
    // ai a gente retorna essa string como resposta da requisicao, com o status 400 Bad Request