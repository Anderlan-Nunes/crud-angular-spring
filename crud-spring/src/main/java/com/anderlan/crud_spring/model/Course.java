package com.anderlan.crud_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Lombok é uma biblioteca que ajuda a reduzir o código boilerplate, como getters e setters. Ele gera o getters e os setters aqui
@Entity // Essa anotação indica que a classe Course é uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.

//**@Table(name = "cursos")** // Essa anotação indica que a tabela no banco de dados se chamará "cursos". Como o hibernate já vai pegar o nome da classe e colocar no banco de dados, não precisa colocar essa anotação. Mas se na empresa ja existisse um banco de dados ao inves de criar um novo, poderia colocar essa anotação para o hibernate não criar uma nova tabela com o nome da classe.

public class Course {

  @Id // Essa anotação indica que o campo id é a chave primária da entidade. O hibernate vai criar um campo id no banco de dados e vai ser a chave primária.
  @GeneratedValue(strategy = GenerationType.AUTO) // Essa anotação indica que o valor do campo id será gerado automaticamente pelo banco de dados. O hibernate vai gerar um valor único para o campo id quando um novo curso for criado.
  @JsonProperty("_id")// essa anotaçao indica que o campo id sera mapeado para o campo _id no json.
  private Long id;

  @Column(length = 200, nullable = false) 
  private String name;

  @Column(length = 20, nullable = false) 
  private String category;
  
}

// @GeneratedValue(strategy = GenerationType.AUTO) // Essa anotação indica que o valor do campo id será gerado automaticamente pelo banco de dados. O hibernate vai gerar um valor único para o campo id quando um novo curso for criado. O strategy é a estratégia de geração do id. O AUTO significa que o hibernate vai escolher a melhor estratégia de geração de id para o banco de dados que você está usando. Você pode usar outras estratégias, como IDENTITY, SEQUENCE ou TABLE, **dependendo do banco de dados e da sua necessidade.**


//  @Column(name = "nome") // Essa anotação indica que o campo name será mapeado para a coluna "nome" na tabela do banco de dados. O hibernate vai criar uma coluna chamada "nome" na tabela e vai mapear o campo name da classe para essa coluna.

//  @Column(length = 200)// Essa anotação indica que o campo name será mapeado para uma coluna no banco de dados. O length indica o tamanho máximo da coluna no banco de dados. O hibernate vai criar uma coluna com o nome name e o tamanho máximo de 200 caracteres. -> padrao eh 255