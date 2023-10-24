package com.example.application.views.tests;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("test")
@PermitAll
@Route(value = "test", layout = MainLayout.class)
public class Test1 extends Composite<VerticalLayout> implements HasUrlParameter<String>{

    static Map<String, List<String>> parametersMap;

@Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {

        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        parametersMap =
                queryParameters.getParameters();
    }

    Map <Integer, String> chosenOptions = new HashMap<>();
    static String option;
    static int i = 1;
    static int numberOfQ = 5;


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

    RadioButtonGroup radioGroup = new RadioButtonGroup();
    TestComponents tc = new TestComponents(typeQ, questions, answers, answerTrue);


    public Test1() {


        H1 h1 = new H1();

        HorizontalLayout layoutRow = new HorizontalLayout();
        Button nextButton = new Button();
        Button previousButton = new Button();
        HorizontalLayout layoutRow2 = new HorizontalLayout();

        getContent().setHeightFull();
        getContent().setWidthFull();

        h1.setText(i + "/" + numberOfQ);
        radioGroup.setLabel(questions.get(i));
        radioGroup.setItems(tc.answers.get(i));
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);

        nextButton.setText("next");
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousButton.setText("previous");
        previousButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);

        getContent().add(h1);
        getContent().add(radioGroup);
        getContent().add(layoutRow);
        layoutRow.add(nextButton);
        layoutRow.add(previousButton);
        getContent().add(layoutRow2);


        radioGroup.addValueChangeListener(event -> {
            if(event.getValue()!= null){
                option = (String) event.getValue();
            }

        });


        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i < numberOfQ) {
                if (option != null)
                    chosenOptions.put(i, option);
                i++;

                nextQuestion(radioGroup);
                option = null;
            }
            h1.setText(i + "/" + numberOfQ);
        });

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i>1) {
                if (option != null)
                    chosenOptions.put(i, option);
                i--;
                previousQuestion(radioGroup);
                option = null;
            }
            h1.setText(i + "/" + numberOfQ);
        });
    }
    private void nextQuestion(RadioButtonGroup radioGroup){
        radioGroup.setLabel(questions.get(i));
        radioGroup.setItems(tc.answers.get(i));
        choiceNotifier(radioGroup);
        System.out.println(Test1.parametersMap);
    }

    private void previousQuestion(RadioButtonGroup radioGroup){
        radioGroup.setLabel(questions.get(i));
        radioGroup.setItems(tc.answers.get(i));
        choiceNotifier(radioGroup);

    }
    private void choiceNotifier (RadioButtonGroup radioButtonGroup){
        if(chosenOptions.get(i)!=null){
            radioButtonGroup.setValue(chosenOptions.get(i));
        }

    }

}
