package com.example.application.service;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.ComparisonAnswer;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.ComparisonAnswerRepository;
import com.example.application.data.repository.QuestionRepository;
import com.example.application.data.repository.TestRepository;
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

}
