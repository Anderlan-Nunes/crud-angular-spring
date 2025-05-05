package com.anderlan.crud_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderlan.crud_spring.model.Course;
import com.anderlan.crud_spring.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController // que ele faz. ele vai falar por Spring que essa classe ela contém uma url que nós vamos poder acessar a nossa API // pos traz do panos isso é um Java servlet que vai ter os métodos do post, do get, delet e etc.

@RequestMapping("/api/courses") // que essa vai ser qual que é realmente o endPoint que vai ficar exposto. Então, essa classe vai ser responsável por fazer o CRUD do couser.

@AllArgsConstructor // vai gerar um construtor para mim. Então, eu não preciso fazer o construtor q serve com injençao de dependencia manualmente.
public class CouserController {

  private final CourseRepository courseRepository; // o repositório é a camada de persistência, ou seja, é onde nós vamos fazer as operações de CRUD no banco de dados. Usar-se *final* é uma boa prática, pois assim o Spring não vai ficar criando instâncias do repositório toda hora. Então, o Spring só vai criar uma instância do repositório quando a classe for instanciada. 
  
  
  /**
  public CouserController(CourseRepository courseRepository) { // o construtor é o que vai fazer a injeção de dependência, ou seja, ele vai pegar o repositório e vai injetar aqui dentro da classe. quando faz isso nao precisa fazer a anotação ->@Autowired-<, porque o Spring já sabe que esse construtor é o que vai fazer a injeção de dependência. mas como eu estou usanto a anotaçao @AllArgsConstructor, eu não preciso fazer isso pq ele ja vai gerar o construtor pra mim.
    this.courseRepository = courseRepository;
  }*/


  @GetMapping
  public List<Course> list() {
    return courseRepository.findAll();
  } 
  
}


/* colocar (/api)
 * colocar/api é importante para a gente conseguir distinguir Quais são os end points por exemplo dum roteamento lá do angula pro roteamento aqui do Servidor 
 * 
 * colacar /api/courses
 * localhost:8080/api/courses é onde eu consigo ter todas ações para cursos: obter lista de cursos, obter curso por id, criar curso, deletar curso, atualizar curso.
 */