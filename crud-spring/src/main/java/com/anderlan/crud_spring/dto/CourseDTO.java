package com.anderlan.crud_spring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseDTO(
    @JsonProperty("_id") Long id,
    @NotBlank @NotNull @Size(min = 3, max = 100) String name,
    @NotNull @Size(max = 20) String category,
    List<LessonDTO> lessons
) {
    
}

//record é uma classe imutável, ou seja, não pode ser alterada depois de criada.