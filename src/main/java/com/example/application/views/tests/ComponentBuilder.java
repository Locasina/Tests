package com.example.application.views.tests;

import com.example.application.data.entity.Question;
import com.example.application.data.repository.QuestionRepository;
import com.example.application.data.repository.TestRepository;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ComponentBuilder {
    List<Question> result;
    ComponentBuilder (List<Question>result, String str) {
        for (Question q:
                result) {
            if(q.getTestId() == Integer.parseInt(str))
                questions.add(q.getText());
        }
    }
    Iterable<Question> somequestion = null;
    static Map<Integer, Integer> typeQ = new HashMap<>();
    static {
        for (int j = 1; j <= 5; j++) {
            typeQ.put(j, 1);
        }
    }
    ArrayList<String> questions = new ArrayList<>();
    static Map<Integer, String[]> answers = new HashMap();
    static {
        answers.put(1, new String[]{"красное", "голубое","жёлтое","Зелёное"});
        answers.put(2, new String[]{"один", "два","три","четыре"});
        answers.put(3, new String[]{"1000", "2000","2023","2007"});
        answers.put(4, new String[]{"Ёлка", "Подснежник","Принтер","Мухамор"});
        answers.put(5, new String[]{"Мучении", "Нраваучении","Сожалении","Бою"});
    }

    static Map<Integer,Integer> answerTrue = new HashMap<>();
    static {
        answerTrue.put(1, 3);
        answerTrue.put(2, 4);
        answerTrue.put(3, 3);
        answerTrue.put(4, 1);
        answerTrue.put(5, 4);
    }
    static TestComponents tc = new TestComponents();
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
