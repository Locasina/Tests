package com.example.application.data.repository;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.MultiChoiceAnswer;
import org.springframework.data.repository.CrudRepository;

public interface MultiChoiceAnswerRepository extends CrudRepository<MultiChoiceAnswer, Integer> {
}
