package com.example.application.data.repository;

import com.example.application.data.entity.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

}
