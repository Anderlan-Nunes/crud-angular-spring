package com.anderlan.crud_spring.enums;

public enum Category {
    BACK_END("Back-end"),
    FRONT_END("Front-end");

    private final String value; // essa varaviavel vai ser o meu valor

    private Category(String value) { // construtor esta privado porque ninguem vai instanciar, e nao quero que essa informacao seja exposta
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

// essa abordagem de usar valores como construtores em enums é útil quando você quer associar uma string ou outro valor a cada constante do enum, facilitando a exibição ou o uso desses valores em outras partes do código.