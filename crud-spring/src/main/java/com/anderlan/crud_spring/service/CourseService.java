package com.anderlan.crud_spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.anderlan.crud_spring.model.Course;
import com.anderlan.crud_spring.repository.CourseRepository;
import com.anderlan.crud_spring.exception.RecordNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository; // o repositório é a camada de persistência, ou seja, é onde nós vamos fazer as operações de CRUD no banco de dados. Usar-se *final* é uma boa prática, pois assim o Spring não vai ficar criando instâncias do repositório toda hora. Então, o Spring só vai criar uma instância do repositório quando a classe for instanciada. 

    public CourseService(CourseRepository courseRepository) { // injeção de dependência via construtor, que é a forma recomendada pelo Spring. Assim, o Spring vai injetar a instância do repositório quando a classe for instanciada; estou usando o construtor aqui pq eu nao vou usar o lombok só para isso como feito no cousercontoller.
        this.courseRepository = courseRepository;
    }

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id, "Curso"));// sempre tem que retornar algo, caso nao retrorne um curso, eu lanço uma exceção. mas a exceção que eu criei.
    }

    public Course create(@RequestBody @Valid Course course) {
        return courseRepository.save(course);
    }

    public Course update(@NotNull @Positive Long id, @Valid Course course) {
    return courseRepository.findById(id)
        .map(recordFound -> {
          recordFound.setName(course.getName());
          recordFound.setCategory(course.getCategory());
          return courseRepository.save(recordFound);
        }).orElseThrow(() -> new RecordNotFoundException(id, "Curso"));
    }

    public void delete(@PathVariable @NotNull @Positive Long id) {

        courseRepository.delete(courseRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id, "Curso"))); // usa essa forma para ser mais consisa e nao retorna nada.

        // courseRepository.findById(id)
        //     .map(recordFound -> {
        //         courseRepository.deleteById(id);
        //         return true;
        // })
        // .orElseThrow(() -> new RecordNotFoundException(id, "Curso"));
    }
}