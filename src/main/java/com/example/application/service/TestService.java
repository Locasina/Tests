package com.example.application.service;

import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import com.example.application.security.SecurityService;
import com.example.application.util.TestData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestService {
    public ComparisonAnswerRepository comparisonAnswerRepository;
    public QuestionRepository questionRepository;
    public AnswerRepository answerRepository;
    public TestRepository testRepository;
    public ChooseRepository chooseRepository;
    public NewCreateTestRepository newCreateTestRepository;
    public SecurityService securityService;
    private List<List<Answer>> getAllTestAnswer(Integer index){
        List<Question> questions = questionRepository.findByTestId(index);
        List<List<Answer>> answers = new ArrayList<>();
        for (Question q:
             questions) {
            answers.add(answerRepository.findByQuestionId(q.getId()));
        }
        return answers;
    }
    private List<List<ComparisonAnswer>> getAllComparisonAnswer(int testID) {
        List<List<ComparisonAnswer>> compAnswers = new ArrayList();
        for (Question q : questionRepository.findByTestId(testID)) {
            if (q.getTypeQ() == 4) {
                compAnswers.add(comparisonAnswerRepository.findByQuestionId(q.getId()));
            } else {
                compAnswers.add(null);
            }
        }
        return compAnswers;
    }
    public TestData dataTestCatch(int index) {
        TestData td = new TestData();
        td.setAnswers(getAllTestAnswer(index));
        td.setQuestions(questionRepository.findByTestId(index));
        td.setCompAnswers(getAllComparisonAnswer(index));
        return td;
    }
    public void addChoose(Answer answer, User user){
        if(answer !=null & user != null) {
            Choose choose = chooseRepository.findTopByOrderByIdDesc();
            if (choose == null) {
                choose = new Choose();
                choose.setId(1);
                choose.setUser(user);
                choose.setAnswer(answer);
                chooseRepository.save(choose);
            } else {
                int id = choose.getId() + 1;
                choose = new Choose();
                choose.setId(id);
                choose.setUser(user);
                choose.setAnswer(answer);
                chooseRepository.save(choose);
            }
        }
    }
    public void deleteChoose(Answer answer, User user) {
        if(answer != null & user != null) {
            Choose choose = chooseRepository.findByAnswerAndUser(answer, user);
            if(choose != null)
                chooseRepository.delete(choose);
        }
    }
    public List<Answer> findAllQChoose(Question question, User user){
        List<Choose> chooseList = chooseRepository.findByUser(user);
        ArrayList<Answer> answerList = new ArrayList<>();
        for (Choose ch:
             chooseList) {
            if (ch.getAnswer().getQuestion().getId() == question.getId())
                answerList.add(ch.getAnswer());
        }
        return answerList;
    }
    public boolean answerCheck(Answer answer) {
    return newCreateTestRepository.findByAnswer(answer).isCorrect();
    }

    public void type2Saver(Answer answer) {
        List<Answer> choose1 = findAllQChoose(answer.getQuestion(), (User) securityService.getAuthenticatedUser());

        Choose choose = new Choose();
        choose.setAnswer(answer);
        choose.setUser((User) securityService.getAuthenticatedUser());
        if(chooseRepository.findTopByOrderByIdDesc() != null)
            choose.setId(chooseRepository.findTopByOrderByIdDesc().getId()+1);
        else choose.setId(1);
        if (choose1.size() == 0) {
            chooseRepository.save(choose);
        }
        else  {
            Choose oldChoose = chooseRepository.findByAnswerAndUser(choose1.get(0), (User) securityService.getAuthenticatedUser());
            chooseRepository.delete(oldChoose);
            chooseRepository.save(choose);
        }
    }

    public Answer find2Answer(Question question) {
        List<Answer> choose1 = findAllQChoose(question, (User) securityService.getAuthenticatedUser());
        Choose oldChoose = chooseRepository.findByAnswerAndUser(choose1.get(0), (User) securityService.getAuthenticatedUser());
        return oldChoose.getAnswer();
    }

}
