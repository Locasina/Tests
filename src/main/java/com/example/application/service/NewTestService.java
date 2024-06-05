package com.example.application.service;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.ComparisonAnswer;
import com.example.application.data.entity.NewCreateTestData;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.ComparisonAnswerRepository;
import com.example.application.data.repository.NewCreateTestRepository;
import com.example.application.data.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NewTestService {
    private NewCreateTestRepository newCreateTestRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    @Autowired
    private ComparisonAnswerRepository comparisonAnswerRepository;

    public void deleteCompAnswers(NewCreateTestData newCreateTestData) {
        if(newCreateTestData.getComparisonAnswer() != null) {
            ComparisonAnswer comparisonAnswer = newCreateTestData.getComparisonAnswer();
            comparisonAnswerRepository.delete(comparisonAnswer);

        }
    }

    public void compAnsDataUpdate (NewCreateTestData newCreateTestData, Question question) {
        if (newCreateTestData.getComparisonAnswer() == null) {
            ComparisonAnswer ca = new ComparisonAnswer();
            ca.setColumn1(newCreateTestData.getText());
            ca.setColumn2(newCreateTestData.getName());
            if (comparisonAnswerRepository.findTopByOrderByIdDesc() == null)
                ca.setId(1);
            else
                ca.setId(comparisonAnswerRepository.findTopByOrderByIdDesc().getId()+1);
            ca.setQuestion(question);
            comparisonAnswerRepository.save(ca);
            newCreateTestData.setComparisonAnswer(ca);
        }
        else {
            ComparisonAnswer ca = newCreateTestData.getComparisonAnswer();
            ca.setColumn1(newCreateTestData.getText());
            ca.setColumn2(newCreateTestData.getName());
            comparisonAnswerRepository.save(ca);
        }
    }

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

    public void setAnswer(int i, String text,  Question question) {
        List<Answer> answers = answerRepository.findByQuestionId(i);
        Answer answer;
        if (answers.size() == 0) {
            answer = new Answer();
            answer.setText(text);
            answer.setQuestion(question);
            answer.setId(answerRepository.findTopByOrderByIdDesc().getId() + 1);
            System.out.println(answer);
        } else {
            answer = answers.get(0);
            answer.setText(text);
        }
        answerRepository.save(answer);
    }

    public String getQuestion(int i) {
        Question question = questionRepository.findById(i);
        return question.getText();
    }

    public String getAnswer(int i) {
        List<Answer> answers = answerRepository.findByQuestionId(i);
        if (answers.size() == 0)
            return "";
        return answers.get(0).getText();
    }
    public Question getQuestionObject(int i) {
        Question question = questionRepository.findById(i);
        return question;
    }
    public void dataUpdate(NewCreateTestData newCreateTestData, Question question) {
        if(newCreateTestData.getAnswer() == null) {
            Answer answer = new Answer();
            if(answerRepository.findTopByOrderByIdDesc() == null)
                answer.setId(1);
            else
                answer.setId(answerRepository.findTopByOrderByIdDesc().getId()+1);
            answer.setText(newCreateTestData.getText());
            answer.setQuestion(question);
            answerRepository.save(answer);
            newCreateTestData.setAnswer(answer);
        }
        else {
            Answer answer = newCreateTestData.getAnswer();
            answer.setText(newCreateTestData.getText());
            newCreateTestData.setAnswer(answer);
        }
        newCreateTestRepository.save(newCreateTestData);
    }
    public void dataUpdate(NewCreateTestData newCreateTestData) {
        newCreateTestRepository.save(newCreateTestData);
    }
    public int getId() {
        if (answerRepository.findTopByOrderByIdDesc() == null)
            return 1;
        else
            return answerRepository.findTopByOrderByIdDesc().getId()+1;
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
        if (newCreateTestData.getAnswer() != null)
            answerRepository.delete(newCreateTestData.getAnswer());
    }

    public void deleteAllData(int qId){
        Question question = questionRepository.findById(qId);
        if(question.getTypeQ() == 3) {
            List<Answer> aList = answerRepository.findByQuestionId(qId);
            if (aList.size() != 0) {
                for (Answer a :
                        aList) {
                    answerRepository.delete(a);
                }
            }
        }
        else if(question.getTypeQ() == 4) {
            List<Answer> answers = answerRepository.findByQuestionId(qId);
            List<NewCreateTestData> dataList = new ArrayList();
            List<ComparisonAnswer> compA = new ArrayList();
            for (Answer x :
                    answers) {
                dataList.add(newCreateTestRepository.findByAnswerId(x.getId()));
            }
            for (NewCreateTestData x :
                    dataList) {
                compA.add(x.getComparisonAnswer());
                newCreateTestRepository.delete(x);
            }
            for (Answer x :
                    answers) {
                answerRepository.delete(x);
            }
            for (ComparisonAnswer x :
                    compA) {
                comparisonAnswerRepository.delete(x);
            }
        }
        else {
            List<Answer> answers = answerRepository.findByQuestionId(qId);
            List<NewCreateTestData> dataList = new ArrayList();
            for (Answer x :
                    answers) {
                dataList.add(newCreateTestRepository.findByAnswerId(x.getId()));
            }
            for (NewCreateTestData x :
                    dataList) {
                newCreateTestRepository.delete(x);
            }
            for (Answer x :
                    answers) {
                answerRepository.delete(x);
            }
        }
        questionRepository.delete(question);
    }

    public void deleteAllData(int[] qIdArr) {
        for (int qId :
                qIdArr) {
            Question question = questionRepository.findById(qId);
            if (question.getTypeQ() == 3) {
                List<Answer> aList = answerRepository.findByQuestionId(qId);
                if (aList.size() != 0) {
                    for (Answer a :
                            aList) {
                        answerRepository.delete(a);
                    }
                }
            } else if(question.getTypeQ() == 4) {
                List<Answer> answers = answerRepository.findByQuestionId(qId);
                List<NewCreateTestData> dataList = new ArrayList();
                List<ComparisonAnswer> compA = new ArrayList();
                for (Answer x :
                        answers) {
                    dataList.add(newCreateTestRepository.findByAnswerId(x.getId()));
                }
                for (NewCreateTestData x :
                        dataList) {
                        if(x.getComparisonAnswer() != null)
                            compA.add(x.getComparisonAnswer());
                        newCreateTestRepository.delete(x);

                }
                for (Answer x :
                        answers) {
                    answerRepository.delete(x);
                }
                for (ComparisonAnswer x :
                        compA) {
                    comparisonAnswerRepository.delete(x);
                }
            }
            else {
                List<Answer> answers = answerRepository.findByQuestionId(qId);
                List<NewCreateTestData> dataList = new ArrayList();
                for (Answer x :
                        answers) {
                    dataList.add(newCreateTestRepository.findByAnswerId(x.getId()));
                }
                for (NewCreateTestData x :
                        dataList) {
                    newCreateTestRepository.delete(x);
                }
                for (Answer x :
                        answers) {
                    answerRepository.delete(x);
                }
            }
            questionRepository.delete(question);
        }
    }
}
