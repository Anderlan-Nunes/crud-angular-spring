package com.anderlan.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.anderlan.crud_spring.enums.Category;
import com.anderlan.crud_spring.model.Course;
import com.anderlan.crud_spring.model.Lesson;
import com.anderlan.crud_spring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean // Spring vai criar um bean do tipo CommandLineRunner, ou seja, ele vai executar esse método quando a aplicação for iniciada.
	CommandLineRunner initDataBase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll(); // Deletar todos os cursos do banco de dados antes de inserir novos cursos.
			Course course = new Course();
			course.setName("Java");
			course.setCategory(Category.BACK_END);

			// aki seta a liçao dentro do curso
			Lesson l= new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("rgEKMHGYyns");
			l.setCourse(course); // agente nao vai setar o identificador agente vai setar o objeto inteiro(a referencia do objeto). O curso aki ja foi setado la em cima. Primeiro sempre seta o objeto principal.
			course.getLessons().add(l);

			Lesson l1= new Lesson();
			l1.setName("angular");
			l1.setYoutubeUrl("ffffs");
			l1.setCourse(course); // agente nao vai setar o identificador agente vai setar o objeto inteiro(a referencia do objeto). O curso aki ja foi setado la em cima. Primeiro sempre seta o objeto principal.
			course.getLessons().add(l1);

			courseRepository.save(course);
		};
	}
}

/*
 * a melhor forma de mapear um relacionamento One-to-Many (um para muitos) no Hibernate/JPA, dando preferência ao relacionamento bidirecional para otimizar a performance.

O trecho que você citou [22:04] se concentra em como o Hibernate/JPA consegue inserir a lição (aula) com o identificador do curso, mesmo que o identificador do curso ainda não esteja disponível no momento em que a lição é criada no código:

1. O Problema e a Solução:
O Desafio [22:04]: Ao criar uma nova Lição (aula), você precisa associá-la a um Curso. No seu código de teste, você está criando e inserindo o Curso e a Lição em sequência. A Lição precisa do curso_id (identificador do curso), mas esse ID só é gerado pelo banco de dados depois que o Curso é inserido.

A Solução [22:13]: A chave é setar o objeto Curso inteiro (usando setCurso(c)), e não o ID, na sua Lição.

2. O Mecanismo do Hibernate/JPA (Ganhando Performance):
Inserção do Objeto Principal [22:25]: O Hibernate/JPA executa primeiro o INSERT do objeto principal do relacionamento, que é o Curso.

Geração do ID [22:36]: Depois que o Curso é inserido no banco, o banco de dados gera o identificador (ID) para esse curso.

Referência do Objeto [22:46]: O objeto Curso (c) que você usou no seu código é uma referência. Quando o Hibernate executa o INSERT do curso, ele atualiza automaticamente o ID dentro desse objeto de referência que está na memória do Java.

Inserção Otimizada da Lição [23:03]: Como você usou a mesma referência do objeto Curso (c) para fazer o setCurso na Lição, a Lição agora também tem acesso a esse ID. Quando o Hibernate/JPA faz o INSERT da Lição, ele já consegue fazer o INSERT com o identificador do curso (curso_id) correto.

3. O Ganho de Performance:
SQL Otimizado [23:44]: Com o mapeamento bidirecional e o uso do setCurso dessa forma, o número de comandos SQL executados é reduzido de três para dois (um INSERT para o Curso e um INSERT para a Lição).

Redução de I/O [23:47]: Isso significa que o sistema vai ao banco de dados "uma vez a menos", pois elimina o UPDATE extra que seria necessário para setar o curso_id na tabela de lições, como aconteceria em um relacionamento unidirecional mal configurado [23:50].

Escalabilidade [23:54]: A Loiane enfatiza que essa otimização é crucial para aplicações com muitos dados. Se um curso tiver mil aulas, você economiza mil comandos de UPDATE no banco de dados, resultando em um grande ganho de performance [23:57].

Em resumo, a preferência é sempre por utilizar o relacionamento bidirecional (@OneToMany de um lado e @ManyToOne do outro) e setar o objeto inteiro (setCurso(c)) em vez de gerenciar IDs, permitindo que o Hibernate/JPA otimize as consultas e vá ao banco de dados menos vezes
 */