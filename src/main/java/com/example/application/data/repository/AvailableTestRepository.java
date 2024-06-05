package com.example.application.data.repository;

import com.example.application.data.entity.AvailableTest;
import com.example.application.data.entity.Test;
import com.example.application.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvailableTestRepository extends CrudRepository<AvailableTest, Integer> {

    List<AvailableTest> findByUserUsername(String username);
    List<AvailableTest> findByTestId(int testId);
    AvailableTest findTopByOrderByIdDesc();

    AvailableTest findByUserAndTest(User user, Test test);

}
