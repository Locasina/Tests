package com.example.application.data.repository;

import com.example.application.data.entity.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findByQuestionId(int id);
    List<Answer> findAllByQuestionId(int id);
}
