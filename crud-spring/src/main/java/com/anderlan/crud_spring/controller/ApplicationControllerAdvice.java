package com.anderlan.crud_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anderlan.crud_spring.exception.RecordNotFoundException;

@RestControllerAdvice // eh a classe que vai dizer para todos os rest controllers o que fazer com as excecoes

public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // quando der a excecao RecordNotFoundException, a gente vai retornar o status 404    
   // vamos ter os metodos que vao tratar as excecoes do jeito que a gente quer usando o nosso RecordNotFoundException
    public String handleNotFoundException(RecordNotFoundException ex){
        return ex.getMessage();
    }  
    
}
