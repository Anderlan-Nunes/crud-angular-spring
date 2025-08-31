package com.anderlan.crud_spring.exception;

public class RecordNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(Long id, String entityType) {
        super(entityType + " não encontrado com id: " + id);
    }
    
}