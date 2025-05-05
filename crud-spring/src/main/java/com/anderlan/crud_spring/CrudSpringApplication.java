package com.anderlan.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.anderlan.crud_spring.model.Course;
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
			course.setCategory("Programação");
			courseRepository.save(course);
		};
	}
}
