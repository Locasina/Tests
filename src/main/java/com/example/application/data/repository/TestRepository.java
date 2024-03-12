package com.example.application.data.repository;

import com.example.application.data.entity.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepository extends CrudRepository<Test, Integer> {
List<Test> findAll();

}
