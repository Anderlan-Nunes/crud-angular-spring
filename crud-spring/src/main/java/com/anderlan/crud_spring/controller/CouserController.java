package com.anderlan.crud_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.anderlan.crud_spring.model.Course;
import com.anderlan.crud_spring.repository.CourseRepository;
import com.anderlan.crud_spring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
// import lombok.AllArgsConstructor; ela mudou para Controller → Service → Repository

@Validated // essa anotação é usada para validar os dados que estão sendo enviados no corpo da requisição. Ela vai validar os dados antes de chegar no método do controller. Então, se os dados não estiverem válidos, o Spring vai retornar um erro 400 (Bad Request) e não vai chamar o método do controller.
// se voce so usar o @Valid nao precisava, mas como estou usando o notNull e o positive, eu preciso usar o @Validated para que o Spring valide esses dados antes de chegar no método do controller.

@RestController // que ele faz. ele vai falar por Spring que essa classe ela contém uma url que nós vamos poder acessar a nossa API // pos traz do panos isso é um Java servlet que vai ter os métodos do post, do get, delet e etc.

@RequestMapping("/api/courses") // que essa vai ser qual que é realmente o endPoint que vai ficar exposto. Então, essa classe vai ser responsável por fazer o CRUD do couser.

//@AllArgsConstructor // vai gerar um construtor para mim. Então, eu não preciso fazer o construtor q serve com injençao de dependencia manualmente.
public class CouserController {

  private final CourseService courseService; // injeção de dependência do service  
  
  public CouserController(CourseService courseService) { // o construtor é o que vai fazer a injeção de dependência, ou seja, ele vai pegar o repositório e vai injetar aqui dentro da classe. quando faz isso nao precisa fazer a anotação ->@Autowired-<, porque o Spring já sabe que esse construtor é o que vai fazer a injeção de dependência. mas como eu estou usanto a anotaçao @AllArgsConstructor, eu não preciso fazer isso pq ele ja vai gerar o construtor pra mim.
    this.courseService = courseService; // injeção de dependência do service
  }

  @GetMapping
  public List<Course> list() {
    return courseService.list(); // ela mudou para Controller → Service → Repository
  }

  @GetMapping("/{id}") // esse método vai receber o id atraves da URL
  public ResponseEntity<Course> fingById(@PathVariable @NotNull @Positive Long id) {
    return courseService.findById(id)
      .map(recordFound -> ResponseEntity.ok().body(recordFound)) // se vier coom a informoção do BD eu vou retornar isso no corpo da minha informação, ou seja, vou retornar o curso que foi encontrado no banco de dados.
      .orElse(ResponseEntity.notFound().build()); // se nao encontrar o curso, eu vou retornar um status 404 (not found) e não vou retornar nada no corpo da resposta.
  }

  //@PostMapping // 1
  // public Course create(@RequestBody Course course) { // 1
  //   // System.out.println(course.getName());
  //   return courseRepository.save(course); // courseRepository eh onde salva o curso no banco de dados.
  // } teve que mudar o metodo para ResponseEntity<Course> para poder retornar o codigo de status 201 em vez de 200

  // //@PostMapping 
  // public ResponseEntity<Course> create(@RequestBody Course course) { 
  //   return ResponseEntity.status(HttpStatus.CREATED)
  //     .body(courseRepository.save(course)); // a vantagem de usar o ResponseEntity é que eu consigo manipular o cabeçalho da resposta. Mas como eu não estou manipulando o cabeçalho, eu poderia usar a anotação @ResponseStatus(HttpStatus.CREATED) e ficaria mais simples. 
  // } 

// uso esse pq só vou retornar o status 201 e não preciso manuzear o response em nenhum outra maneira.
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)

  public Course create(@RequestBody @Valid Course course) {
    return courseService.create(course);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
    return courseService.update(id, course)
        .map(recordFound -> ResponseEntity.ok().body(recordFound)) // se encontrar o curso, eu vou atualizar o curso e retornar o curso atualizado no corpo da resposta.
        .orElse(ResponseEntity.notFound().build()); // se nao encontrar o curso, eu vou retornar um status 404 (not// found) e não vou retornar nada no corpo da resposta.
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
     if (courseService.delete(id))
        return ResponseEntity.noContent().<Void>build(); // se encontrar o curso, eu vou deletar o curso e retornar um status 204 (no content) e não vou retornar nada no corpo da resposta.
     return ResponseEntity.notFound().build();
  }
}


/* colocar (/api)
 * colocar/api é importante para a gente conseguir distinguir Quais são os end points por exemplo dum roteamento lá do angula pro roteamento aqui do Servidor 
 * 
 * colacar /api/courses
 * localhost:8080/api/courses é onde eu consigo ter todas ações para cursos: obter lista de cursos, obter curso por id, criar curso, deletar curso, atualizar curso.
 */

/* 1
* @PostMapping
 * CRUD Angular + Spring | 21: Formulário: API Spring: Criar Curso (HTTP POST)
 * https://docs.google.com/document/d/1HhoaX8St9iWSKVH743M8bMJWDNw3Uvwk-Owb8aoc8SE/edit?tab=t.2l4szpema22n
 *para receber o payload (dados do formulário) enviado no corpo do request (da requisição HTTP POST pelo Angular), o Spring oferece uma forma automática de fazer isso [04:10]. ou seja sem precisar fazer manusear o json que esta vindo no corpo do nosso request.
 para isso, nós vamos usar a anotação @RequestBody(ou seja eh o corpo do request). Essa anotação vai fazer com que o Spring pegue o corpo do request e converta para o objeto que nós passamos como parâmetro. Então, nesse caso, nós vamos passar o Course como parâmetro. O Spring vai pegar o corpo do request e converter para um objeto Course.
 * 
 */

/* 2 - @ResponseStatus x ResponseEntity
 * decidiu usar a anotação @ResponseStatus no Spring como uma alternativa ao ResponseEntity para simplificar o código
 * Ambas as abordagens alcançam o mesmo objetivo de definir o código de status HTTP retornado pela API [14:57, 15:05].
 *  No entanto, a vantagem de ResponseEntity é a capacidade de manipular os cabeçalhos da resposta. Como neste caso a intenção era apenas retornar o status de "created" (201) sem nenhuma manipulação adicional no cabeçalho, a anotação @ResponseStatus tornou o código mais conciso e limpo
 */

/* 3 - ("/{id}") @PathVariable Long id
 * quando usamos o ("/{id}") temo s que usar o @PathVariable Long id
 * O @PathVariable é uma anotação do Spring que indica que o valor do parâmetro id deve ser extraído da URL da requisição. Isso permite que o método receba o id do curso diretamente da URL, facilitando a busca pelo curso específico no banco de dados.
 * Pode tambem usar assim :
 * @PutMapping("/{id}")
 * public Course update(@PathVariable("id") Long identificadorQualquerNome)
*/