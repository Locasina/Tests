package com.example.application.views.tests;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Question;

import java.util.*;


public class ComponentController {

     public Map<Integer, Integer> typeQ;
     Map<Integer, ArrayList> answers;
     ArrayList<String> answersText;                                                                                             //текст ответов
     Map<Integer, String> questionsText;


    ComponentController(List<Question> sourceQuestions, List<Answer> answer, String str) {

        typeQ = new HashMap<>();
        questionsText = new HashMap<>();

        answers = new HashMap<>();

        int t = 1;


        for (Question q: sourceQuestions) {
            if(q.getTest().getId() == Integer.parseInt(str)) {
                typeQ.put(t, q.getTypeQ());
                questionsText.put(t, q.getText());
                answersText = new ArrayList<>();

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
