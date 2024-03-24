package com.example.application.service;

import com.example.application.data.entity.MyTest;
import com.example.application.data.entity.Test;
import com.example.application.data.entity.User;
import com.example.application.data.repository.MyTestRepository;
import com.example.application.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyTestService {
    SecurityService securityService;
    MyTestRepository myTestRepository;
    public List<MyTest> getMyTest() {
        List<MyTest> e = myTestRepository.findByUserUsername(securityService.getAuthenticatedUser().getUsername());
        return e;
    }
    public void saveNewTest(Test test){
        MyTest myTest = new MyTest();
        myTest.setTest(test);
        myTest.setUser((User) securityService.getAuthenticatedUser());
        myTest.setState(0);
        myTest.setId((int) myTestRepository.count()+1);
        myTestRepository.save(myTest);
    }

}
