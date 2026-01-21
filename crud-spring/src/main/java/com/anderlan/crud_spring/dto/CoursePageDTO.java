package com.anderlan.crud_spring.dto;

import java.util.List;

// esse record so vai ser utilizada como leitura, nao precisa das validações.
public record CoursePageDTO(List<CourseDTO> courses, long totalElements, int totalPages) { 
    
}
