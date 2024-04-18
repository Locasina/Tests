package com.example.application.views.createtest;

import com.example.application.data.entity.NewCreateTestData;
import com.example.application.service.NewTestService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;
@PermitAll
@Route("help")
public class GridBufferedInlineEditor extends VerticalLayout {

    public GridBufferedInlineEditor(NewTestService newTestService) {
        ValidationMessage firstNameValidationMessage = new ValidationMessage();
        ValidationMessage lastNameValidationMessage = new ValidationMessage();

        // tag::snippet[]
        Grid<NewCreateTestData> grid = new Grid<>(NewCreateTestData.class, false);
        Editor<NewCreateTestData> editor = grid.getEditor();

        Grid.Column<NewCreateTestData> firstNameColumn = grid
                .addColumn(NewCreateTestData::getText).setHeader("First name")
                .setWidth("120px").setFlexGrow(0);
        Grid.Column<NewCreateTestData> lastNameColumn = grid.addColumn(NewCreateTestData::getText)
                .setHeader("Last name").setWidth("120px").setFlexGrow(0);
        Grid.Column<NewCreateTestData> editColumn = grid.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);


        Binder<NewCreateTestData> binder = new Binder<>(NewCreateTestData.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField firstNameField = new TextField();
        firstNameField.setWidthFull();
        binder.forField(firstNameField)
                .asRequired("First name must not be empty")
                .withStatusLabel(firstNameValidationMessage)
                .bind(NewCreateTestData::getText, NewCreateTestData::setText);
        firstNameColumn.setEditorComponent(firstNameField);

        TextField lastNameField = new TextField();
        lastNameField.setWidthFull();
        binder.forField(lastNameField).asRequired("Last name must not be empty")
                .withStatusLabel(lastNameValidationMessage)
                .bind(NewCreateTestData::getText, NewCreateTestData::setText);
        lastNameColumn.setEditorComponent(lastNameField);

        Button saveButton = new Button("Save", e -> editor.save());
        saveButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
//                System.out.println(editor.getItem().getText());
                newTestService.changeText(1, "Вот такая вот новая строка");
                UI.getCurrent().getPage().reload();
            }
        });
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);
        // end::snippet[]

        editor.addCancelListener(e -> {
            firstNameValidationMessage.setText("");
            lastNameValidationMessage.setText("");
        });

        List<NewCreateTestData> people = newTestService.getText();
        grid.setItems(people);

        getThemeList().clear();
        getThemeList().add("spacing-s");
        add(grid, firstNameValidationMessage, lastNameValidationMessage);
    }

}
