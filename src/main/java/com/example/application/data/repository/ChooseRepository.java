package com.example.application.data.repository;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Choose;
import com.example.application.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChooseRepository extends CrudRepository<Choose, Integer> {
    Choose findTopByOrderByIdDesc();
    Choose findByAnswerAndUser(Answer answer, User user);
    List<Choose> findByUser(User user);
}
