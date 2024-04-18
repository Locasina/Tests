package com.example.application.data.repository;

import com.example.application.data.entity.NewCreateTestData;
import org.springframework.data.repository.CrudRepository;

public interface NewCreateTestRepository extends CrudRepository<NewCreateTestData, Integer> {
    NewCreateTestData findById(int i);
    NewCreateTestData findByAnswerId(int answerId);
}
