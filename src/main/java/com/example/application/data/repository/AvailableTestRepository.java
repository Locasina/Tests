package com.example.application.data.repository;

import com.example.application.data.entity.AvailableTest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvailableTestRepository extends CrudRepository<AvailableTest, Integer> {

    List<AvailableTest> findByUserUsername(String username);

}
