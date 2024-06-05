package com.example.application.views.testlist;

import com.example.application.data.entity.User;
import com.example.application.security.SecurityService;
import com.example.application.service.MyTestService;
import com.example.application.views.MainLayout;
import com.example.application.views.tests.Test1;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("My View")
@Route(value = "Preview/:testID", layout = MainLayout.class)
@PermitAll
public class PreviewPage extends Composite<VerticalLayout> implements BeforeEnterObserver {
    @Autowired
    MyTestService myTestService;
    @Autowired
    SecurityService userSevice;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        testID = event.getRouteParameters().get("testID").get(); //todo
        System.out.println(testID);


    }

    String testID;

    public PreviewPage() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        Paragraph textMedium = new Paragraph();
        Paragraph textMedium2 = new Paragraph();
        Paragraph textMedium3 = new Paragraph();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h2.setText("Название теста");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, h2);
        h2.setWidth("max-content");
        textMedium.setText("Время прохождения теста");
        textMedium.setWidth("300px");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        textMedium2.setText("Тест доступен с Х по Y");
        textMedium2.setWidth("300px");
        textMedium2.setHeight("26px");
        textMedium2.getStyle().set("font-size", "var(--lumo-font-size-m)");
        textMedium3.setText(
                "После нажатия кнопки \"Начать\" таймер запустится и будет работать в реальном времени. Убедитесь, что вы подготовились к прохождению и уложитесь во временные рамки.");
        textMedium3.setWidth("100%");
        textMedium3.getStyle().set("font-size", "var(--lumo-font-size-m)");
        buttonPrimary.setText("Начать");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(textMedium);
        layoutColumn2.add(textMedium2);
        layoutColumn2.add(textMedium3);
        layoutColumn2.add(buttonPrimary);

        buttonPrimary.addClickListener(e ->     //todo for redirect to preview
                getUI().ifPresent(ui -> {
                        myTestService.userStartTest((User)userSevice.getAuthenticatedUser(), myTestService.findTestByMytestId(Integer.parseInt(testID)));
                        ui.navigate(Test1.class, new RouteParameters("testID", testID));

                }
                )
        );
    }
}
