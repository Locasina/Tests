package com.example.application.views.tests;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import java.util.ArrayList;
import java.util.Map;

public class TestComponents {
    Integer typeQ;
    ArrayList<String> questions;
    Map<Integer, ArrayList> answers;
    Map<Integer,Integer> answerTrue;
    RadioButtonGroup radioGroup;
    HorizontalLayout layoutRow;
    HorizontalLayout layoutRow2;
    HorizontalLayout layoutRow3;

    public TestComponents(Integer typeQ, ArrayList<String> questions, Map<Integer, ArrayList> answers, Map<Integer, Integer> answerTrue, RadioButtonGroup radioGroup, HorizontalLayout layoutRow, HorizontalLayout layoutRow2, HorizontalLayout layoutRow3) {
        this.typeQ = typeQ;
        this.questions = questions;
        this.answers = answers;
        this.answerTrue = answerTrue;
        this.radioGroup = radioGroup;
        this.layoutRow = layoutRow;
        this.layoutRow2 = layoutRow2;
        this.layoutRow3 = layoutRow3;
    }

    public TestComponents(Integer typeQ, ArrayList<String> questions, Map<Integer, ArrayList> answers, Map<Integer, Integer> answerTrue) {
        this.typeQ = typeQ;
        this.questions = questions;
        this.answers = answers;
        this.answerTrue = answerTrue;
    }
    public TestComponents(){}
}
