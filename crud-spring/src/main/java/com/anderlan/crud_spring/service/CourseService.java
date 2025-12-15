package com.anderlan.crud_spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.anderlan.crud_spring.repository.CourseRepository;
import com.anderlan.crud_spring.dto.CourseDTO;
import com.anderlan.crud_spring.dto.mapper.CourseMapper;
import com.anderlan.crud_spring.exception.RecordNotFoundException;
import com.anderlan.crud_spring.model.Course;

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

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO)
        .orElseThrow(() -> new RecordNotFoundException(id, "Curso"));// sempre tem que retornar algo, caso nao retrorne um curso, eu lanço uma exceção. mas a exceção que eu criei.
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    // a lista de aula esta altamento acoplada ao curso
    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id)
            .map(recordFound -> {
                Course course = courseMapper.toEntity(courseDTO);
                recordFound.setName(courseDTO.name());
                recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));// Aqui estou colocando "Backend" fixo(hardcode) -> POR ENQUNTO<- , mas poderia ser qualquer valor que esteja dentro do enum.só para parar de dar erro.
                //recordFound.setLessons(course.getLessons());
                recordFound.getLessons().clear(); // limpa a lista de aulas existentes no curso encontrado.
                course.getLessons().forEach(recordFound.getLessons()::add);// pega cada aula que veio no meu mapper e vou adicionar manualmente
                return courseMapper.toDTO(courseRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id, "Curso"));
    }

    public void delete(@NotNull @Positive Long id) {

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

/*
 * @PathVariable não é necessário aqui, lugar de usar ele eh no controller. pois é ele que vai receber a requisição HTTP.  pois ele é usado para mapear o valor da variável na URL para o parâmetro do método no controller. Aqui no service, não estamos lidando com URLs, então não faz sentido usar essa anotação.
 */

/**
 *  Abordagem Atual: API Acoplada
Características
Todas as operações passam pelo recurso Course
Lessons está altamente acoplada ao Course
API não expõe Lesson como recurso independente

 */