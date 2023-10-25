package com.example.application.views.tests;

import java.util.HashMap;
import java.util.Map;

public class ComponentBuilder {
    static Map<Integer, Integer> typeQ = new HashMap<>();
    static {
        for (int j = 1; j <= 5; j++) {
            typeQ.put(j, 1);
        }
    }
    static Map<Integer, String> questions = new HashMap<>();
    static {
        questions.put(1, "Какого цвета солнце");
        questions.put(2, "Сколько углов у квадрата");
        questions.put(3, "Какой сейчас год");
        questions.put(4, "Зимой и летом одним цветом");
        questions.put(5, "Тяжело в учении легко в ...");
    }
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
    static TestComponents getComponents(String str) {
        if (str.equals("1")) {
            questions.put(1, "Сколько дней в году");
            questions.put(2, "Круглый ли круг");
            questions.put(3, "АААА");
            questions.put(4, "ВВВ");
            questions.put(5, "ДТ");
            return new TestComponents(typeQ, questions, answers, answerTrue);
        }
        else {
            questions.put(1, "Какого цвета солнце");
            questions.put(2, "Сколько углов у квадрата");
            questions.put(3, "Какой сейчас год");
            questions.put(4, "Зимой и летом одним цветом");
            questions.put(5, "Тяжело в учении легко в ...");
            System.out.println(str + " " + str.equals("1"));
            return new TestComponents(typeQ, questions, answers, answerTrue);
        }
    }
}
