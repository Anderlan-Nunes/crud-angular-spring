package com.anderlan.crud_spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity // Indica que esta clase vai representar uma tabela no banco de dados
public class Lesson {

    @Id // Essa anotação indica que o campo id é a chave primária da entidade. O  hibernate vai criar um campo id no banco de dados e vai ser a chave primária.
    @GeneratedValue(strategy = GenerationType.AUTO) // Essa anotação indica que o valor do campo id será gerado automaticamente pelo banco de dados. O hibernate vai gerar um valor único para o campo id quando um novo curso for criado.
    private Long id;

    @Column(length = 100, nullable = false) // poderia deixar vazio e usar os valores padrão, mas não é uma boa prática.
    private String name;

    @Column(length = 11, nullable = false)
    private String youtubeUrl;

    // vamos mapear o course tambem aqui para fazer o relacionamento bidirecional, Aki o relacionamento é ManyToOne, ou seja, muitas aulas para um curso. muitos para um (N:1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // FetchType.LAZY indica que o curso associado a uma aula só será carregado do banco de dados quando for acessado pela primeira vez. Só quando chamar o getCourse desta liçao eh que vai carregar o mapeamento
    @JoinColumn(name = "course_id", nullable = false) // essa anotação indica que a tabela Lesson terá uma coluna chamada course_id que será a chave estrangeira para a tabela Course.
    private Course course;
}
    