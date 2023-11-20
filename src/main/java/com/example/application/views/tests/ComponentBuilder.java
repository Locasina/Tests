package com.example.application.views.tests;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComponentBuilder {

     Integer typeQ;


       ArrayList<String> answersText;                                                                                             //текст ответов

    //  List<Question> result;

    ArrayList<String> questions = new ArrayList<>();

    Map<Integer, ArrayList> answers = new HashMap();
    ComponentBuilder (List<Question> result, List<Answer> answer, String str) {


        for (Question q: result) {
            if(q.getTestId() == Integer.parseInt(str)) {
                typeQ = q.getTypeQ();
                questions.add(q.getText());
                 answersText = new ArrayList();

                for (int i = 0; i < answer.size(); i++) {
                    if(q.getQuestionId() == answer.get(i).getQuestionId()) {
                            answersText.add(answer.get(i).getText());
                    }
                }
                answers.put(q.getQuestionId(), answersText);
            }
        }

    }
    Iterable<Question> someQuestion = null;

    static Map<Integer,Integer> answerTrue = new HashMap<>();
    static {
        answerTrue.put(1, 3);
        answerTrue.put(2, 4);
        answerTrue.put(3, 3);
        answerTrue.put(4, 1);
        answerTrue.put(5, 4);
    }

    TestComponents getComponents(String str) {

        return new TestComponents(typeQ, questions, answers, answerTrue);
//
//        if (str.equals("1")) {
//            questions.add(1, "Сколько дней в году");
//            questions.add(2, "Круглый ли круг");
//            questions.add(3, "АААА");
//            questions.add(4, "ВВВ");
//            questions.add(5, "ДТ");
//            return new TestComponents(typeQ, questions, answers, answerTrue);
//        }
//        else {
//            questions.add(1, "Какого цвета солнце");
//            questions.add(2, "Сколько углов у квадрата");
//            questions.add(3, "Какой сейчас год");
//            questions.add(4, "Зимой и летом одним цветом");
//            questions.add(5, "Тяжело в учении легко в ...");
//            return new TestComponents(typeQ, questions, answers, answerTrue);
//        }
    }
}
