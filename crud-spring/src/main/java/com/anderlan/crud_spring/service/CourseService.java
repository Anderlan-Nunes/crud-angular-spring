package com.anderlan.crud_spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.anderlan.crud_spring.repository.CourseRepository;
import com.anderlan.crud_spring.dto.CourseDTO;
import com.anderlan.crud_spring.dto.mapper.CourseMapper;
import com.anderlan.crud_spring.exception.RecordNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository; // o repositório é a camada de persistência, ou seja, é onde nós vamos fazer as operações de CRUD no banco de dados. Usar-se *final* é uma boa prática, pois assim o Spring não vai ficar criando instâncias do repositório toda hora. Então, o Spring só vai criar uma instância do repositório quando a classe for instanciada. 
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) { // injeção de dependência via construtor, que é a forma recomendada pelo Spring. Assim, o Spring vai injetar a instância do repositório quando a classe for instanciada; estou usando o construtor aqui pq eu nao vou usar o lombok só para isso como feito no cousercontoller.
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list() {
        return courseRepository.findAll().stream().map(courseMapper::toDTO)
            .collect(Collectors.toList()); // estou usando o stream para transformar a lista de cursos em uma lista de CourseDTO. O map vai pegar cada curso e vai transformar em um CourseDTO usando o método toDTO do CourseMapper. E o collect vai transformar o stream em uma lista.
    }

    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO)
        .orElseThrow(() -> new RecordNotFoundException(id, "Curso"));// sempre tem que retornar algo, caso nao retrorne um curso, eu lanço uma exceção. mas a exceção que eu criei.
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO course) {
    return courseRepository.findById(id)
        .map(recordFound -> {
          recordFound.setName(course.name());
          recordFound.setCategory(course.category());
          return courseMapper.toDTO(courseRepository.save(recordFound));
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