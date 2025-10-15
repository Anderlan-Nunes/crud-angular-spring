package com.anderlan.crud_spring.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

import com.anderlan.crud_spring.enums.Status;

@Converter(autoApply = true) // autoApply = true faz com que o JPA aplique esse conversor automaticamente para todas as entidades que usarem o enum Category. Assim, nao preciso ficar colocando @Convert(converter = CategoryConverter.class) em cada entidade que usar o enum Category.
public  class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status category) {
        if (category == null) {
            return null;
        }
        return category.getValue(); // aqui estou retornando o valor do enum, que é uma String.
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        // aqui estou convertendo a String de volta para o enum, usando o valor.
        return Stream.of(Status.values())
            .filter(c -> c.getValue().equals(value))
            .findFirst() // eu sei que só vai retornar somente um valor, pq o valor é unico.
            .orElseThrow(IllegalArgumentException::new); // se nao encontrar, lança uma exceção.; essa exceção é uma exceção genérica, mas poderia ser uma exceção personalizada. ela é uma exceção de tempo de execução(runtime exception)
    }
    
}

// essa classe é usada para converter o enum Category para String e vice-versa, para que possamos armazenar o valor do enum no banco de dados como uma String. 

/*
 * trata sobre como configurar um conversor (Converter) no Spring Data JPA para que um enumerador (enum) salve um valor específico (como uma String) no banco de dados, em vez de salvar o nome do próprio membro do enumerador.

Isso é necessário porque um enumerador pode ter múltiplos atributos e, consequentemente, o Spring precisa de uma instrução clara sobre o que exatamente persistir.
 */

 // equals(value)) nao tem nescessidade de usar o ignorecase pq ja estou comparando com o valor exato do enum.
