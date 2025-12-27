package com.anderlan.crud_spring.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LessonDTO(
    Long id,
    @NonNull @NotBlank @Size(min = 3, max = 100) String name,
    @NonNull @NotBlank @Size(min = 10, max = 11)String youtubeUrl
) {
    
}
// colocar mesma validação que na entidade Lesson