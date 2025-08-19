package com.anderlan.crud_spring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Lombok é uma biblioteca que ajuda a reduzir o código boilerplate, como getters e setters. Ele gera o getters e os setters aqui
@Entity // Essa anotação indica que a classe Course é uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.

//**@Table(name = "cursos")** // Essa anotação indica que a tabela no banco de dados se chamará "cursos". Como o hibernate já vai pegar o nome da classe e colocar no banco de dados, não precisa colocar essa anotação. Mas se na empresa ja existisse um banco de dados ao inves de criar um novo, poderia colocar essa anotação para o hibernate não criar uma nova tabela com o nome da classe.

@SQLDelete(sql = "UPDATE Course SET status = 'Inactive' WHERE id = ?") // Essa anotação indica que quando um curso for deletado, ele não será realmente removido do banco de dados, mas sim atualizado para o status "Inactive". Isso é útil para manter o histórico dos cursos e evitar a exclusão física dos dados.
@SQLRestriction("status = 'Active'")

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
  @Size(max = 20)
  @Pattern(regexp = "Backend|Frontend|Fullstack")
  @Column(length = 20, nullable = false) 
  private String category;

  @NotNull
  @Size(max = 10) // validação em tempo de execução
  @Pattern(regexp = "Active|Inactive")
  @Column(length = 10, nullable = false) // é uma anotação de persistência que define o tamanho da coluna no banco de dados
  private String status = "Active"; // valor padrão para o status, caso não seja informado na criação do curso.

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