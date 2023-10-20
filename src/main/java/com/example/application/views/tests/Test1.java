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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

import java.util.HashMap;
import java.util.Map;

@PageTitle("test1")
@PermitAll
@Route(value = "test1", layout = MainLayout.class)
public class Test1 extends Composite<VerticalLayout> {

    Map <Integer, String> chosenOptions = new HashMap<>();
    static String option;
    static int i = 1;
    static int numberOfQ = 15;


    public Test1() {


        H1 h1 = new H1();
        RadioButtonGroup radioGroup = new RadioButtonGroup();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button nextButton = new Button();
        Button previousButton = new Button();
        HorizontalLayout layoutRow2 = new HorizontalLayout();

        getContent().setHeightFull();
        getContent().setWidthFull();

        h1.setText(i + "/15");
        radioGroup.setLabel("Radio Group");
        radioGroup.setItems("Order ID", "Product Name", "Customer", "Status");
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
                chosenOptions.put(i, option);
                i++;

                nextQuestion(radioGroup);
            }
            h1.setText(i + "/" + numberOfQ);
        });
        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i>1) {
                i--;
                previousQuestion(radioGroup);;
            }
            h1.setText(i + "/" + numberOfQ);
        });
    }
    private void nextQuestion(RadioButtonGroup radioGroup){
        radioGroup.setItems("Order ID", "Product Name", "Customer", "Status");
        choiceNotifier(radioGroup);
    }

    private void previousQuestion(RadioButtonGroup radioGroup){
        radioGroup.setItems("Order ID", "Product Name", "Customer", "Status");
        System.out.println(radioGroup);
        choiceNotifier(radioGroup);
    }
    private void choiceNotifier (RadioButtonGroup radioButtonGroup){
        if(chosenOptions.get(i)!=null){
            radioButtonGroup.setValue(chosenOptions.get(i));
        }

    }

}
