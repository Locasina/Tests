package com.example.application.service;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.NewCreateTestData;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.NewCreateTestRepository;
import com.example.application.data.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NewTestService {
    private NewCreateTestRepository newCreateTestRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public List<NewCreateTestData> getText() {
        ArrayList<NewCreateTestData> arrayList = new ArrayList();
        NewCreateTestData ctd = new NewCreateTestData();
        ctd.setText(newCreateTestRepository.findById(1).getText());
        arrayList.add(ctd);
        return arrayList;
    }
    public  void changeText(int index, String newStr){
        NewCreateTestData nctd = newCreateTestRepository.findById(index);
        nctd.setText(newStr);
        newCreateTestRepository.save(nctd);
    }
    public void setQuestion(int i, String text) {
        Question question = questionRepository.findById(i);
        question.setText(text);
        questionRepository.save(question);
    }
    public String getQuestion(int i) {
        Question question = questionRepository.findById(i);
        return question.getText();
    }
    public Question getQuestionObject(int i) {
        Question question = questionRepository.findById(i);
        return question;
    }
    public void dataUpdate(NewCreateTestData newCreateTestData, Question question) {
        if(newCreateTestData.getAnswer() == null) {
            Answer answer = new Answer();
            answer.setId((int) (answerRepository.count()+2));
            answer.setText(newCreateTestData.getText());
            answer.setQuestion(question);
            answerRepository.save(answer);
            newCreateTestData.setAnswer(answer);
        }
        else {
            Answer answer = newCreateTestData.getAnswer();
            answer.setText(newCreateTestData.getText());
            newCreateTestData.setAnswer(answer);
            System.out.println("lol");
        }
        newCreateTestRepository.save(newCreateTestData);
    }
    public int getId() {
        return (int) newCreateTestRepository.count()+1;
    }

    public List<NewCreateTestData> getNewTestListByQuestionId(Integer id) {
        List<Answer> answers = answerRepository.findByQuestionId(id);
        List<NewCreateTestData> dataList = new ArrayList();
        for (Answer x:
             answers) {
            dataList.add(newCreateTestRepository.findByAnswerId(x.getId()));
        }
        return dataList;
    }
    public void deleteData(NewCreateTestData newCreateTestData){
        newCreateTestRepository.delete(newCreateTestData);
        answerRepository.delete(newCreateTestData.getAnswer());
    }

    public void deleteAllData(int qId){
        List<Answer> answers = answerRepository.findByQuestionId(qId);
        List<NewCreateTestData> dataList = new ArrayList();
        for (Answer x:
                answers) {
            dataList.add(newCreateTestRepository.findByAnswerId(x.getId()));
        }
        for (NewCreateTestData x:
             dataList) {
            newCreateTestRepository.delete(x);
        }
        for (Answer x:
             answers) {
            answerRepository.delete(x);
        }
        questionRepository.delete(questionRepository.findById(qId));
    }
}
