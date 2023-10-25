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

    String str = "";

@Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        i = 1;
        str = queryParameters.getQueryString();
        tc = ComponentBuilder.getComponents(str);
        update();
    }

    H1 h1 = new H1();

    HorizontalLayout layoutRow = new HorizontalLayout();
    Button nextButton = new Button();
    Button previousButton = new Button();
    HorizontalLayout layoutRow2 = new HorizontalLayout();

    Map <Integer, String> chosenOptions = new HashMap<>();
    static String option;
    static int i = 1;
    static int numberOfQ = 5;

    RadioButtonGroup radioGroup = new RadioButtonGroup();
    TestComponents tc = ComponentBuilder.getComponents("1");

    public Test1() {



        getContent().setHeightFull();
        getContent().setWidthFull();


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
        tc = ComponentBuilder.getComponents(str);
        radioGroup.setLabel(tc.questions.get(i));
        radioGroup.setItems(tc.answers.get(i));
        choiceNotifier(radioGroup);
        System.out.println(str.equals("1"));
    }

    private void previousQuestion(RadioButtonGroup radioGroup){
        radioGroup.setLabel(tc.questions.get(i));
        radioGroup.setItems(tc.answers.get(i));
        choiceNotifier(radioGroup);

    }
    private void choiceNotifier (RadioButtonGroup radioButtonGroup){
        if(chosenOptions.get(i)!=null){
            radioButtonGroup.setValue(chosenOptions.get(i));
        }

    }

    private void update(){
        h1.setText(i + "/" + numberOfQ);
        radioGroup.setLabel(tc.questions.get(i));
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
    }


}
