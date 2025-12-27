package com.anderlan.crud_spring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourseDTO(
    // bassicamente esses sao as mesmas validações que temos na entidade Course
    @JsonProperty("_id") Long id,
    @NotBlank @NotNull @Size(min = 3, max = 100) String name,
    @NotNull @Size(max = 20) @Pattern(regexp = "^(Back-end|Front-end)$") String category,
    @NotNull @NotEmpty @Valid List<LessonDTO> lessons
) {
    
}


//record é uma classe imutável, ou seja, não pode ser alterada depois de criada.

/**
 * Quando você cria um DTO (Data Transfer Object), precisa garantir que as validações estejam presentes tanto no DTO quanto na entidade JPA. Por quê?
Fluxo de uma Requisição

Cliente envia dados → Controller recebe o DTO
DTO é validado (se tiver @Valid ou @Validated)
DTO é convertido para Entidade (usando mapper)
Entidade é persistida no banco de dados

DTO = primeira linha de defesa (valida antes de processar)
Entidade = última linha de defesa (garante integridade no banco)
Sempre duplique as validações no DTO e na Entidade
Use @Valid no Controller para ativar validação automática do Spring

Isso evita processamento desnecessário e falhas tardias no fluxo da aplicação.

como o Spring faz a validação automática no Controller:
A "Mágica" do Spring com @Valid
Quando você adiciona @Valid (ou @Validated) no parâmetro do Controller, o Spring ativa um mecanismo automático de validação antes do seu método ser executado.


@Pattern(regexp = "^(Back-end|Front-end)$")
^$ indica o início e o fim da string, garantindo que toda a string corresponda exatamente a um dos valores especificados. Se não usasse isso, poderia aceitar valores como "XBack-endY".
 */