package com.example.application.data.repository;

import com.example.application.data.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

    List<Question> findByTestId(int id);

}
