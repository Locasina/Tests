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

@PageTitle("test1")
@PermitAll
@Route(value = "test1", layout = MainLayout.class)
public class Test1 extends Composite<VerticalLayout> {
    static int i = 1;
    static int numberOfQ = 15;

    private void nextQuestion(RadioButtonGroup radioGroup){
        radioGroup.setItems("Order ID", "Product Name", "Customer", "Status");
    }

    private void previousQuestion(RadioButtonGroup radioGroup){
        radioGroup.setItems("Order ID", "Product Name", "Customer", "Status");
    }

    public Test1() {


        H1 h1 = new H1();
        RadioButtonGroup radioGroup = new RadioButtonGroup();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonPrimary2 = new Button();
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
        buttonPrimary.setText("next");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.setText("не некст");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        getContent().add(h1);
        getContent().add(radioGroup);
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonPrimary2);
        getContent().add(layoutRow2);

        buttonPrimary.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                if (i < numberOfQ) {
                    i++;
                    nextQuestion(radioGroup);
                }
                h1.setText(i + "/" + numberOfQ);
            }
        });
        buttonPrimary2.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                if (i>1) {
                    i--;
                    previousQuestion(radioGroup);;
                }
                h1.setText(i + "/" + numberOfQ);
            }
        });
    }

}
