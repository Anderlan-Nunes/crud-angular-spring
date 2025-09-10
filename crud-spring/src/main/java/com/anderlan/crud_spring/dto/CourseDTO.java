package com.anderlan.crud_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourseDTO(
    @JsonProperty Long id,
    @NotBlank @NotNull @Size(min = 3, max = 100) String name,
    @NotNull @Size(max = 20) @Pattern(regexp = "Backend|Frontend|Fullstack") String category
) {
    
}

//record é uma classe imutável, ou seja, não pode ser alterada depois de criada.