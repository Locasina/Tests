package com.example.application.data.repository;

import com.example.application.data.entity.MyTest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MyTestRepository extends CrudRepository<MyTest, Integer> {
    List<MyTest> findByUserUsername(String username);
    List<MyTest> findAll();
    MyTest findById(int id);
}
