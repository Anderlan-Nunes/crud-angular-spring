package com.anderlan.crud_spring.dto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.anderlan.crud_spring.dto.CourseDTO;
import com.anderlan.crud_spring.dto.LessonDTO;
import com.anderlan.crud_spring.enums.Category;
import com.anderlan.crud_spring.model.Course;

@Component // mesma coisa que o @Service, só que é mais específico para componentes que fazem mapeamento. Então, o Spring vai gerenciar essa classe como um bean, ou seja, ele vai criar uma instância dessa classe e vai injetar onde for necessário.
public class CourseMapper {
    
   public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        List<LessonDTO> lessons = course.getLessons()
            .stream()
            .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
            .toList();
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
            lessons); // como o Category é um enum, precisamos converter ele para String. Aqui estou colocando "Backend" fixo -> POR ENQUNTO<- , mas poderia ser qualquer valor que esteja dentro do enum.
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
        course.setCategory(convertCategoryValue(courseDTO.category())); // Aqui estou colocando "Backend" fixo(hardcode) -> POR ENQUNTO<- , mas poderia ser qualquer valor que esteja dentro do enum.só para parar de dar erro.
        return course;
    }

    public Category convertCategoryValue(String value) { // método para converter a String que vem do DTO para o enum Category. poderia tambem usar o stream.
        if(value == null) {
            return null;
        }
        return switch(value) { // switch expression (disponível a partir do Java 14)
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Invalid category value: " + value);
        };
    }


}

// estudar depois sobre padrão builder (builder pattern) para fazer o mapeamento.

/**
 * "Nós vamos ver o que será feito com cada aula (lição) que retornar da nossa lista principal de dados [03:09]. É importante notar que cada aula aqui é um dado completo (uma 'entidade'), e não apenas um objeto simplificado (um 'DTO') [03:16]. Para cada uma dessas aulas, vamos criar um novo item simplificado ('DTO') [03:20], que vai carregar apenas o código (identificador), o nome e a URL do YouTube [03:27]. Por fim, todos esses novos itens simplificados serão reunidos e colocados novamente dentro de uma lista [03:34]."
 * 
 * para cada
lição que retornar do nosso da nossa
lista que é uma entidade, Lembrando que
essa lesson aqui é do tipo lesson mesmo e
não é um dto; nós vamos pegar e nós
vamos criar um novo dto passando
identificador o nome e também pegando a
URL do YouTube no final nós vamos pegar
tudo o que foi mapeado e nós vamos jogar
novamente aí dentro de uma lista
e eu
vou pedir também para adicionar todos os
impostos que estão faltando
e assim para de dar esses erros de
compilação
com isso feito nós vamos pegar essa
variável e no lugar de ser cursos.getlessons a gente vai colocar aqui apenas
lessons
 */
