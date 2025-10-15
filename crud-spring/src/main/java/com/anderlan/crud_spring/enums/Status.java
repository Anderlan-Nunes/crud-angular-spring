package com.anderlan.crud_spring.enums;

public enum Status {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String value; // essa varaviavel vai ser o meu valor

    private Status(String value) { // construtor esta privado porque ninguem vai instanciar, e nao quero que essa informacao seja exposta
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //tostring
    @Override
    public String toString() {
        return value;
    }    
}
