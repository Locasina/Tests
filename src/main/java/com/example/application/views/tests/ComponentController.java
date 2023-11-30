package com.example.application.views.tests;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComponentController {

     ArrayList typeQ;
     Map<Integer, ArrayList> answers;
     ArrayList<String> answersText;                                                                                             //текст ответов
     ArrayList<String> questionsText;


    ComponentController(List<Question> sourceQuestions, List<Answer> answer, String str) {

        typeQ = new ArrayList();
        questionsText = new ArrayList<>();
        answersText = new ArrayList<>();
        answers = new HashMap<>();
        int t = 1;

        for (Question q: sourceQuestions) {
            if(q.getTestId() == Integer.parseInt(str)) {
                typeQ.add(q.getTypeQ());
                questionsText.add(q.getText());
                answersText = new ArrayList();

                for (int i = 0; i < answer.size(); i++) {
                    if(q.getId() == answer.get(i).getQuestion().getId()) {
                        answersText.add(answer.get(i).getText());
                    }
                }
                answers.put(t, answersText);
                t++;
            }
        }

    }
    public ComponentController() {}

    public ComponentController getComponents () {
        return new ComponentController();

    }

}
