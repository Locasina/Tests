package com.example.application.views.finishPage;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Hello World")
@Route(value = "result", layout = MainLayout.class)
@AnonymousAllowed
public class Finish extends VerticalLayout {


    Finish() {
        H1 titleTest = new H1("Тест по БД");
        Button checkAnswer =  new Button("Вернутся к вопросам");
        //<theme-editor-local-classname>
        checkAnswer.addClassName("finish-button-1");
        H1 resultText = new H1("Вы ответили правильно на 3/4 (75%)");
        checkAnswer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        setAlignSelf(Alignment.CENTER,titleTest, checkAnswer,resultText );
        add(titleTest, checkAnswer, resultText);
    }

}
