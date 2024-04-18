package com.example.application.views.createtest;

import com.example.application.data.entity.NewCreateTestData;
import com.example.application.service.NewTestService;
import com.example.application.views.CreateTest;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Admin view that is registered dynamically on admin user login.
 * <p>
 * Allows CRUD operations for the book categories.
 */
@PermitAll
@Route(value = "createTest/:testID/:createQ", layout = MainLayout.class)
public class AdminView extends VerticalLayout implements BeforeEnterObserver {
    int questionId;

    public static final String VIEW_NAME = "Admin";

    private  VirtualList<NewCreateTestData> categoriesListing;
    private  ListDataProvider<NewCreateTestData> dataProvider;
    String qText = "";
    private  Button newCategoryButton;
    @Autowired
    NewTestService newTestService;
    TextField questionField = new TextField();
    TextField textField = new TextField();
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        questionId = Integer.parseInt(beforeEnterEvent.getRouteParameters().get("createQ").get());
        questionField.setValue(newTestService.getQuestion(questionId));
        textField.setValue(String.valueOf(questionId));
        categoriesListing = new VirtualList<>();
        dataProvider = new ListDataProvider<NewCreateTestData>(
                new ArrayList<>(newTestService.getNewTestListByQuestionId(questionId)));
        categoriesListing.setDataProvider(dataProvider);
        categoriesListing.setRenderer(
                new ComponentRenderer<>(this::createCategoryEditor));

        newCategoryButton = new Button("Добавить вариант ответа", event -> {
            final NewCreateTestData newCreateTestData = new NewCreateTestData();
            dataProvider.getItems().add(newCreateTestData);
            dataProvider.refreshAll();
        });
        newCategoryButton.setDisableOnClick(true);


        add(new H2("Введите вопрос"), questionField, new H4("Добавить вариант ответа"), newCategoryButton,
                categoriesListing);
        questionField.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
                newTestService.setQuestion(questionId, questionField.getValue());
            }
        });
        Button backButton = new Button("Назад");
        backButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            getUI().ifPresent(ui ->
                    ui.navigate(CreateTest.class, new RouteParameters("testID", String.valueOf(questionId))));
        });
        add(backButton);
    }
    public AdminView(NewTestService newTestService) {

    }

    private Component createCategoryEditor(NewCreateTestData newCreateTestData) {
        final TextField nameField = new TextField();
//        if (newCreateTestData.getId() < 0) {
//            nameField.focus();
//        }
        final Checkbox trueAnswer = new Checkbox();
        setAlignSelf(FlexComponent.Alignment.CENTER, trueAnswer);
        if(newCreateTestData.getId() == null) {
            newCreateTestData.setId(newTestService.getId());
        }

        final Button deleteButton = new Button(
                VaadinIcon.MINUS_CIRCLE_O.create(), event -> {

            // Ask for confirmation before deleting stuff
            final ConfirmDialog dialog = new ConfirmDialog(
                    "Please confirm",
                    "Are you sure you want to delete the category? Books in this category will not be deleted.",
                    "Delete", (e) -> {
//                DataService.get()
//                        .deleteCategory(category.getId());
                dataProvider.getItems().remove(newCreateTestData);
                dataProvider.refreshAll();
                newTestService.deleteData(newCreateTestData);
                Notification.show("Ответ удалён");
            });

            dialog.open();

        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        final BeanValidationBinder<NewCreateTestData> binder = new BeanValidationBinder<>(
                NewCreateTestData.class);
        binder.forField(nameField).bind("text");
        binder.forField(trueAnswer).bind("correct");
        binder.setBean(newCreateTestData);
        binder.addValueChangeListener(event -> {
            if (binder.isValid()) {
//                DataService.get().updateCategory(category);
                deleteButton.setEnabled(true);
                newCategoryButton.setEnabled(true);
                System.out.println(newCreateTestData.getId());
                newTestService.dataUpdate(newCreateTestData, newTestService.getQuestionObject(questionId));
                Notification.show("Вариант ответа сохранён");
            }
        });
//        deleteButton.setEnabled(category.getId() > 0);

        final HorizontalLayout layout = new HorizontalLayout(nameField,trueAnswer,
                deleteButton);
        layout.setFlexGrow(1);
        return layout;
    }
}