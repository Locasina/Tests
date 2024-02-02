package com.example.application.data.repository;

import com.example.application.data.entity.ComparisonAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComparisonAnswerRepository extends CrudRepository<ComparisonAnswer, Integer> {
    List<ComparisonAnswer> findByQuestionId(int id);
//    List<String> findByColumn1(int id);
}
