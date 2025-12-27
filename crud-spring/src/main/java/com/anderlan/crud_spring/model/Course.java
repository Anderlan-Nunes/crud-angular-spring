package com.anderlan.crud_spring.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.anderlan.crud_spring.enums.Category;
import com.anderlan.crud_spring.enums.Status;
import com.anderlan.crud_spring.enums.converters.CategoryConverter;
import com.anderlan.crud_spring.enums.converters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // Essa anotação indica que a classe Course é uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.

//**@Table(name = "cursos")** // Essa anotação indica que a tabela no banco de dados se chamará "cursos". Como o hibernate já vai pegar o nome da classe e colocar no banco de dados, não precisa colocar essa anotação. Mas se na empresa ja existisse um banco de dados ao inves de criar um novo, poderia colocar essa anotação para o hibernate não criar uma nova tabela com o nome da classe.

@SQLDelete(sql = "UPDATE Course SET status = 'Inactive' WHERE id = ?") // Essa anotação indica que quando um curso for deletado, ele não será realmente removido do banco de dados, mas sim atualizado para o status "Inactive". Isso é útil para manter o histórico dos cursos e evitar a exclusão física dos dados.
@SQLRestriction("status = 'Ativo'")

public class Course {

  @Id // Essa anotação indica que o campo id é a chave primária da entidade. O hibernate vai criar um campo id no banco de dados e vai ser a chave primária.
  @GeneratedValue(strategy = GenerationType.AUTO) // Essa anotação indica que o valor do campo id será gerado automaticamente pelo banco de dados. O hibernate vai gerar um valor único para o campo id quando um novo curso for criado.
  @JsonProperty("_id")// essa anotaçao indica que o campo id sera mapeado para o campo _id no json.
  private Long id;

  @NotBlank // nao deixa ser um espaço
  @NotNull // not null nao deixa ser nulo e tambem nao deixa ser vazio
  @Size(min = 3, max = 100)
  @Column(length = 100, nullable = false) 
  private String name;

  @NotNull
  //@Size(max = 20)
  //@Pattern(regexp = "Backend|Frontend|Fullstack")
  @Column(length = 20, nullable = false) 
  //@Enumerated(EnumType.STRING) // essa anotação indica que o campo category é um enum e que ele será armazenado no banco de dados como um número inteiro (ordinal). O valor do enum será armazenado como o índice do enum (0, 1, 2, ...). Se eu usasse EnumType.STRING, o valor do enum seria armazenado como uma string (BACK_END, FRONT_END, ...).
  @Convert(converter = CategoryConverter.class) // essa anotação indica que o campo category será convertido para o banco de dados usando a classe CategoryConverter. Essa classe é responsável por converter o enum para uma string e vice-versa.
  private Category category;

  @NotNull
  @Column(length = 10, nullable = false) // é uma anotação de persistência que define o tamanho da coluna no banco de dados
  @Convert(converter = StatusConverter.class) // essa anotação indica que o campo status será convertido para o banco de dados usando a classe StatusConverter. Essa classe é responsável por converter o enum para uma string e vice-versa.
  private Status status = Status.ACTIVE;// valor padrão para o status, caso não seja informado na criação do curso.

  @NotNull
  @NotEmpty // nao deixa ser vazio, usado para collection (coleções), arrays e map, char sequences (strings). Aqui é uma lista(collection), por isso usamos o NotEmpty.
  @Valid // essa anotação indica que os objetos dentro da lista lessons também devem ser validados. Ou seja, as validações definidas na classe Lesson serão aplicadas a cada objeto da lista lessons.
  // começar a declarar a associação entre Course e Lesson de um para muitos (1:N) // a classe dona do relacionamento é a classe Course, pois ela contém a lista de Lessons. A classe Lesson contém a referência para o Course, mas não contém a lista de Lessons.u 
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course") // essa anotação indica que a associação entre Course e Lesson é de um para muitos (1:N).mappedBy indica que o lado dono da relação é o lado Many (Lesson) e que o campo que mapeia essa relação na classe Lesson é o campo course. CascadeType.ALL indica que todas as operações (persistir, atualizar, deletar) feitas no Course serão propagadas para as Lessons associadas a ele. OrphanRemoval = true indica que quando uma Lesson for removida da lista de Lessons do Course, ela será deletada do banco de dados.
  //@JoinColumn(name = "course_id") // essa anotação indica que a tabela Lesson terá uma coluna chamada course_id que será a chave estrangeira para a tabela Course.
  private List<Lesson> lessons = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }

  
  
}

// @GeneratedValue(strategy = GenerationType.AUTO) // Essa anotação indica que o valor do campo id será gerado automaticamente pelo banco de dados. O hibernate vai gerar um valor único para o campo id quando um novo curso for criado. O strategy é a estratégia de geração do id. O AUTO significa que o hibernate vai escolher a melhor estratégia de geração de id para o banco de dados que você está usando. Você pode usar outras estratégias, como IDENTITY, SEQUENCE ou TABLE, **dependendo do banco de dados e da sua necessidade.**


//  @Column(name = "nome") // Essa anotação indica que o campo name será mapeado para a coluna "nome" na tabela do banco de dados. O hibernate vai criar uma coluna chamada "nome" na tabela e vai mapear o campo name da classe para essa coluna.

//  @Column(length = 200)// Essa anotação indica que o campo name será mapeado para uma coluna no banco de dados. O length indica o tamanho máximo da coluna no banco de dados. O hibernate vai criar uma coluna com o nome name e o tamanho máximo de 200 caracteres. -> padrao eh 255

/*@Column x @Size
 * @Size(min=..., max=...) é validação (Bean Validation). Garante que a string tenha entre min e max caracteres no nível da aplicação (antes de persistir / ao validar DTOs).

@Column(length = ..., nullable = ...) é mapeamento JPA. Diz ao provedor JPA/ao gerador de schema qual será o tamanho da coluna no banco (por exemplo VARCHAR(100)) e se deve aceitar NULL.

O que cada um faz na prática
@Size impede que valores muito curtos/longos passem na validação (por exemplo, quando você usa @Valid em controladores Spring MVC). Se violado, você recebe um erro de validação e não prossegue.
 */
/*get e set
Eu não sou muito fã de deixar os getters e setters aqui. Eu prefiro ter métodos como adicionarLicao() e removerLicao(), porque essas propriedades aqui não são propriedades que você quer que tenham acesso diretamente, já que você pode modificar a referência desse array aqui.
Então essa é uma outra coisa também que eu vou deixar para vocês pensarem: se realmente vale a pena a gente ter métodos getLessons() e setLessons() aqui, no lugar de criar um método adicionarLicao(), um outro método para removerLicao(), ou um outro método para limpar todas as lições que nós temos nesse curso, no lugar de deixar esse setter, já que a gente pode remover essa referência aqui do Java.

*/