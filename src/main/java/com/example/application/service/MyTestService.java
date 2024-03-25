package com.example.application.service;

import com.example.application.data.entity.MyTest;
import com.example.application.data.entity.Test;
import com.example.application.data.entity.User;
import com.example.application.data.repository.MyTestRepository;
import com.example.application.data.repository.TestRepository;
import com.example.application.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyTestService {
    SecurityService securityService;
    MyTestRepository myTestRepository;
    TestRepository testRepository;
    public List<MyTest> getMyTest() {
        List<MyTest> e = myTestRepository.findByUserUsername(securityService.getAuthenticatedUser().getUsername());
        return e;
    }
    public MyTest saveNewTest(Test test){
        MyTest myTest = new MyTest();
        myTest.setTest(test);
        myTest.setUser((User) securityService.getAuthenticatedUser());
        myTest.setState(0);
        List<MyTest> myTests = myTestRepository.findAll();
        if(myTests.size()>0) {
            myTest.setId(myTests.get(myTests.size() - 1).getId() + 1);
        }
        else myTest.setId(1);
        myTestRepository.save(myTest);
        return myTest;
    }

    public void deleteTest(Test test, MyTest myTest) {
        myTestRepository.delete(myTest);
        testRepository.delete(test);
    }
    public MyTest findById(int i) {
        return myTestRepository.findById(i);
    }

}
