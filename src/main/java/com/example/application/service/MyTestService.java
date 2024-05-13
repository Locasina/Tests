package com.example.application.service;

import com.example.application.data.entity.MyTest;
import com.example.application.data.entity.Question;
import com.example.application.data.entity.Test;
import com.example.application.data.entity.User;
import com.example.application.data.repository.MyTestRepository;
import com.example.application.data.repository.QuestionRepository;
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
    QuestionRepository questionRepository;

    public String getTitle(int i){
        return findById(i).getTest().getTitle();
    }
    public String getSubtitle(int i){
        return findById(i).getTest().getSubtitle();
    }
    public String getText(int i){
        return findById(i).getTest().getText();
    }

    public void allInfSave(int i, String title, String subtitle, String text){
        MyTest mt = findById(i);
        mt.getTest().setTitle(title);
        mt.getTest().setSubtitle(subtitle);
        mt.getTest().setText(text);
        testRepository.save(mt.getTest());
    }
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

    public Question createQuestion(int testId, String str) {
        Question question = new Question();
        question.setTypeQ(setQ(str));
        question.setId((int) (questionRepository.count()+2));
        question.setTest(testRepository.findById(testId));
        question.setText("");
        questionRepository.save(question);
        return question;
    }
    public List<Question> findAllQ(int testID) {
        return questionRepository.findByTestId(testID);
    }

    private int setQ(String str) {
        if (str.indexOf("first") != -1)
            return 1;
        if (str.indexOf("second") != -1)
            return 2;
        if (str.indexOf("third") != -1)
            return 3;
        if (str.indexOf("fourth") != -1)
            return 4;
        return 0;
    }

}
