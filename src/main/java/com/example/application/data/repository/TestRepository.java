package com.example.application.data.repository;

import com.example.application.data.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Integer> {

}
