package com.anderlan.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anderlan.crud_spring.model.Course;

@Repository // o que é o repositório? O repositório é a camada de persistência, ou seja, é onde nós vamos fazer as operações de CRUD no banco de dados.
public interface CourseRepository extends JpaRepository<Course, Long> {

  
}
