package com.anderlan.crud_spring.dto.mapper;

import org.springframework.stereotype.Component;

import com.anderlan.crud_spring.dto.CourseDTO;
import com.anderlan.crud_spring.enums.Category;
import com.anderlan.crud_spring.model.Course;

@Component // mesma coisa que o @Service, só que é mais específico para componentes que fazem mapeamento. Então, o Spring vai gerenciar essa classe como um bean, ou seja, ele vai criar uma instância dessa classe e vai injetar onde for necessário.
public class CourseMapper {
    
   public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseDTO(course.getId(), course.getName(), "BACK_END"); // como o Category é um enum, precisamos converter ele para String. Aqui estou colocando "Backend" fixo -> POR ENQUNTO<- , mas poderia ser qualquer valor que esteja dentro do enum.
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) {
            return null;
        }
        
        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(Category.BACK_END); // Aqui estou colocando "Backend" fixo(hardcode) -> POR ENQUNTO<- , mas poderia ser qualquer valor que esteja dentro do enum.só para parar de dar erro.
        return course;
    }
}

// estudar depois sobre padrão builder (builder pattern) para fazer o mapeamento.
