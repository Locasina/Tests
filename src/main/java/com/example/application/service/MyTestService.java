package com.example.application.service;

import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import com.example.application.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MyTestService {
    SecurityService securityService;
    MyTestRepository myTestRepository;
    TestRepository testRepository;
    QuestionRepository questionRepository;
    UserRepository userRepository;
    @Autowired
    AvailableTestRepository availableTestRepository;

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

    public int[] getIDQ(int testID) {
        List<Question> qList = questionRepository.findByTestId(testID);
        int[] arr = new int[qList.size()];
        int i =0;
        for (Question q: qList) {
            arr[i] = q.getId();
            i++;
        }
        return arr;
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
        if(questionRepository.findTopByOrderByIdDesc() == null)
            question.setId(1);
        else
            question.setId(questionRepository.findTopByOrderByIdDesc().getId()+1);
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
            return 2;
        if (str.indexOf("second") != -1)
            return 1;
        if (str.indexOf("third") != -1)
            return 3;
        if (str.indexOf("fourth") != -1)
            return 4;
        return 0;
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
    public List<User> findAllUserByTestId(int id) {
        List<User> user = new ArrayList<>();
        for (AvailableTest at: availableTestRepository.findByTestId(findTestByMytestId(id).getId())) {
            user.add(at.getUser());
            System.out.println(user);
        }
        return user;
    }
    public void addUserAvailableTest(User user, Test test) {
        AvailableTest availableTest = new AvailableTest();
        availableTest.setUser(user);
        availableTest.setState(-1);
        if(availableTestRepository.findTopByOrderByIdDesc() == null)
            availableTest.setId(1);
        else
            availableTest.setId(availableTestRepository.findTopByOrderByIdDesc().getId()+1);
        availableTest.setTest(test);
        availableTestRepository.save(availableTest);
    }
    public void userStartTest(User user, Test test) {
        AvailableTest availableTest = availableTestRepository.findByUserAndTest(user, test);
        availableTest.setState(2);
        availableTestRepository.save(availableTest);
    }


    public void removeUserAvailableTest(User user, Test test) {
        AvailableTest availableTest = availableTestRepository.findByUserAndTest(user, test);
        availableTestRepository.delete(availableTest);
    }

    public Test findTestByMytestId(int id) {
        return myTestRepository.findById(id).getTest();
    }
    public boolean checkUser(User user, Test test) {
        AvailableTest availableTest = availableTestRepository.findByUserAndTest(user, test);
        return availableTest != null;
    }
    public void deleteUserTest(List<AvailableTest> availableTests){
        for (AvailableTest x:
        availableTests) {
            availableTestRepository.delete(x);
        }
    }

    public List<AvailableTest> findAllAUserByTestId(int id){
        List<AvailableTest> list = availableTestRepository.findByTestId(id);
        return list;
    }

    public void realiseTest(List<AvailableTest> al){
        for (AvailableTest x: al) {
            x.setState(0);
            availableTestRepository.save(x);
        }
    }
    public LocalDateTime getStartDate(int id){
       return myTestRepository.findById(id).getStartDateTime();
    }
    public LocalDateTime getEndDate(int id){
        return myTestRepository.findById(id).getEndDateTime();
    }
    public LocalTime getTimer(int id) {
        return myTestRepository.findById(id).getTestTimer();
    }
    public void setStartDate(LocalDateTime localDateTime, int id){
        MyTest mt = myTestRepository.findById(id);
        mt.setStartDateTime(localDateTime);
        myTestRepository.save(mt);
    }
    public void setEndDate(LocalDateTime localDateTime, int id){
        MyTest mt = myTestRepository.findById(id);
        mt.setEndDateTime(localDateTime);
        myTestRepository.save(mt);
    }
    public void setTimer(LocalTime localTime, int id){
        MyTest mt = myTestRepository.findById(id);
        mt.setTestTimer(localTime);
        myTestRepository.save(mt);
    }


}
