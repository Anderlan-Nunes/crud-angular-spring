package com.anderlan.crud_spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.anderlan.crud_spring.model.Course;
import com.anderlan.crud_spring.repository.CourseRepository;

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

    public Optional<Course> findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id);// se nao encontrar o curso, eu vou retornar um status 404 (not found) e não vou retornar nada no corpo da resposta.
        // ainda falta exceção...
    }

    public Course create(@RequestBody @Valid Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> update(@NotNull @Positive Long id, @Valid Course course) {
    return courseRepository.findById(id)
        .map(recordFound -> {
          recordFound.setName(course.getName());
          recordFound.setCategory(course.getCategory());
          return courseRepository.save(recordFound);
        });
    }

    public boolean delete(@PathVariable @NotNull @Positive Long id) {
     return courseRepository.findById(id)
        .map(recordFound -> {
          courseRepository.deleteById(id);
          return true;
        })
        .orElse(false);
    }
}
