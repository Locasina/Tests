package com.example.application.views;

import com.example.application.views.about.AboutView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Route(value = "createTest/:testID", layout = MainLayout.class)
@PermitAll

public class CreateTest extends Composite<VerticalLayout> implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event){

    }
    public CreateTest(){
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        TextArea textArea = new TextArea();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Checkbox checkbox = new Checkbox();
        TimePicker timePicker = new TimePicker();
        Checkbox checkbox2 = new Checkbox();
        DateTimePicker dateTimePicker = new DateTimePicker();
        DateTimePicker dateTimePicker2 = new DateTimePicker();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Select select = new Select();
        getContent().addClassName(LumoUtility.Gap.XSMALL);
        getContent().addClassName(LumoUtility.Padding.XSMALL);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.addClassName(LumoUtility.Gap.XSMALL);
        layoutColumn2.addClassName(LumoUtility.Padding.XSMALL);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setHeight("200px");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        textField.setLabel("Название теста");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, textField);
        textField.setWidth("min-content");
        textField2.setLabel("Подзаголовок");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, textField2);
        textField2.setWidth("min-content");
        textArea.setLabel("Описание");
        textArea.setWidth("300px");
        layoutColumn3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.addClassName(LumoUtility.Gap.SMALL);
        layoutColumn3.addClassName(LumoUtility.Padding.XSMALL);
        layoutColumn3.setWidth("100%");
        layoutColumn3.setHeight("100px");
        layoutRow2.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow2.addClassName(LumoUtility.Padding.XSMALL);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        checkbox.setLabel("Выкл");
        checkbox.addClickListener((ComponentEventListener<ClickEvent<Checkbox>>) checkboxClickEvent -> timePicker.setEnabled(!timePicker.isEnabled()));
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, checkbox);
        checkbox.setWidth("min-content");
        timePicker.setLabel("Time picker");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, timePicker);
        timePicker.setWidth("min-content");
        checkbox2.setLabel("Выкл");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, checkbox2);
        checkbox2.setWidth("min-content");
        dateTimePicker.setLabel("Date time picker");
        dateTimePicker.setWidth("250px");
        dateTimePicker2.setLabel("Date time picker");
        dateTimePicker2.setWidth("250px");
        layoutColumn4.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.addClassName(LumoUtility.Padding.XSMALL);
        layoutColumn4.setWidth("100%");
        layoutColumn4.getStyle().set("flex-grow", "1");
        layoutRow3.setWidthFull();
        layoutColumn4.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        select.setLabel("Select");
        select.setWidth("min-content");
        setSelectSampleData(select);
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(textField);
        layoutRow.add(textField2);
        layoutRow.add(textArea);
        getContent().add(layoutColumn3);
        layoutColumn3.add(layoutRow2);
        layoutRow2.add(checkbox);
        layoutRow2.add(timePicker);
        layoutRow2.add(checkbox2);
        layoutRow2.add(dateTimePicker);
        layoutRow2.add(dateTimePicker2);
        getContent().add(layoutColumn4);
        layoutColumn4.add(layoutRow3);
        layoutRow3.add(select);
        Button addButton = new Button("Добавить вопрос");
        layoutRow3.add(addButton);
        layoutRow3.getStyle().set("flex-grow", "1");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.END, addButton);
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Button deleteButton = new Button("Удалить тест");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        Button saveButton = new Button("Сохранить тест");
        layoutColumn4.add(layoutRow4);
        layoutRow4.add(deleteButton, saveButton);
        layoutRow4.getStyle().set("flex-grow", "1");

        checkbox2.addClickListener((ComponentEventListener<ClickEvent<Checkbox>>) checkboxClickEvent -> {
            if(dateTimePicker.isEnabled()) {
                dateTimePicker.setEnabled(false);
                dateTimePicker2.setEnabled(false);
            }
            else {
                dateTimePicker.setEnabled(true);
                dateTimePicker2.setEnabled(true);
            }
        });
        deleteButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Удалить \"тест\"?");
            dialog.setText(
                    "Вы уверены что хотите удалить этот тест?");
            dialog.setCancelable(true);
            dialog.setConfirmText("Delete");
            dialog.setConfirmButtonTheme("error primary");
            dialog.open();
            dialog.addConfirmListener(event -> getUI().ifPresent(ui ->
                    ui.navigate(AboutView.class)));
        });

    }
    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setSelectSampleData(Select select) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", null));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        select.setItems(sampleItems);
        select.setItemLabelGenerator(item -> ((SampleItem) item).label());
        select.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
}