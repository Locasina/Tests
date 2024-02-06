package com.example.application.service;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.ComparisonAnswerRepository;
import com.example.application.data.repository.QuestionRepository;
import com.example.application.data.repository.TestRepository;
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
    public List<List<Answer>> getAlltestAnswer(Integer index){
        List<Question> questions = questionRepository.findByTestId(index);
        List<List<Answer>> answers = new ArrayList<>();
        for (Question q:
             questions) {
            answers.add(answerRepository.findByQuestionId(q.getId()));
        }
        return answers;
    }
}
