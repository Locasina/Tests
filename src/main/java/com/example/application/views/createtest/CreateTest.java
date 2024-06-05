package com.example.application.views.createtest;

import com.example.application.data.entity.AvailableTest;
import com.example.application.data.entity.Question;
import com.example.application.data.entity.User;
import com.example.application.data.repository.TestRepository;
import com.example.application.service.MyTestService;
import com.example.application.service.NewTestService;
import com.example.application.views.MainLayout;
import com.example.application.views.about.AboutView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Route(value = "createTest/:testID", layout = MainLayout.class)
@PermitAll

public class CreateTest extends Composite<VerticalLayout> implements BeforeEnterObserver {
    int testID;
    TestRepository testRepository;
    MyTestService myTestService;
    NewTestService newTestService;
    String title = "";

    TextField titleTest;
    TextField subtitleTest;
    TextArea text;

    private VirtualList<Question> qListing;
    private ListDataProvider<Question> qDataProvider;

    private VirtualList<User> userListing;
    private ListDataProvider<User> userDataProvider;

    DateTimePicker dateTimePicker;
    DateTimePicker dateTimePicker2;
    TimePicker timePicker;
    Checkbox checkbox;
    Checkbox checkbox2;

    @Autowired
    void setAutowired(TestRepository testRepository, MyTestService myTestService, NewTestService newTestService){
        this.testRepository = testRepository;
        this.myTestService = myTestService;
        this.newTestService = newTestService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event){
        testID = Integer.parseInt(event.getRouteParameters().get("testID").get());
        System.out.println(testID);
        title = myTestService.getTitle(testID);
        titleTest.setValue(title);
        subtitleTest.setValue(myTestService.getSubtitle(testID));
        text.setValue(myTestService.getText(testID));

        qListing = new VirtualList<>();
        qDataProvider = new ListDataProvider<Question>(
                new ArrayList<>(myTestService.findAllQ(testID)));
        qListing.setDataProvider(qDataProvider);
        qListing.setRenderer(
                new ComponentRenderer<>(this::createQComponent));
        getContent().add(qListing);

        userListing = new VirtualList<>();
        userDataProvider = new ListDataProvider<User>(
                new ArrayList<>(myTestService.findAllUserByTestId(testID)));
        userListing.setDataProvider(userDataProvider);
        userListing.setRenderer(
                new ComponentRenderer<>(this::createUserComponent));
        ComboBox<User> userBox = new ComboBox<>("Пользователи");
        userBox.setItems(myTestService.findAllUser());
        Button addUserButton = new Button("Добавить");
        addUserButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                    if(userBox.getValue()!=null)
                userDataProvider.getItems().add(userBox.getValue());
                userDataProvider.refreshAll();
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(userBox, addUserButton);
        horizontalLayout.setAlignSelf(FlexComponent.Alignment.END, addUserButton);
        getContent().add(horizontalLayout);
        getContent().add(userListing);
        Button saveButton = new Button("Сохранить тест");
        saveButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                myTestService.allInfSave(testID, titleTest.getValue(), subtitleTest.getValue(), text.getValue());
            }
        });
        Button deleteButton = new Button("Удалить тест");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Удалить \"тест\"?");
            dialog.setText(
                    "Вы уверены что хотите удалить этот тест?");
            dialog.setCancelable(true);
            dialog.setConfirmText("Delete");
            dialog.setConfirmButtonTheme("error primary");
            dialog.open();
            myTestService.deleteUserTest(myTestService.findAllAUserByTestId(myTestService.findTestByMytestId(testID).getId()));
            newTestService.deleteAllData(myTestService.getIDQ(testID));
            myTestService.deleteTest(myTestService.findById(testID).getTest(),myTestService.findById(testID));
            dialog.addConfirmListener(e -> getUI().ifPresent(ui ->
                    ui.navigate(AboutView.class)));
        });

        Button releaseButton = new Button("Опубликовать тест");
        releaseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        releaseButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                List<AvailableTest> al = myTestService.findAllAUserByTestId(myTestService.findTestByMytestId(testID).getId());
                myTestService.realiseTest(al);
            }
        });
        HorizontalLayout hw = new HorizontalLayout(saveButton, deleteButton, releaseButton);
        getContent().add(hw);
        dateTimePicker.setValue(myTestService.getStartDate(testID));
        dateTimePicker2.setValue(myTestService.getEndDate(testID));
        timePicker.setValue(myTestService.getTimer(testID));
        if(timePicker.getValue() == null) {
            checkbox.setValue(false);
            timePicker.setEnabled(false);
        }
        else {
            checkbox.setValue(true);
        }
        if(dateTimePicker.getValue() == null & dateTimePicker2.getValue()==null) {
            checkbox2.setValue(false);
            dateTimePicker.setEnabled(false);
            dateTimePicker2.setEnabled(false);
        }
        else {
            checkbox2.setValue(true);
        }

        dateTimePicker2.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DateTimePicker, LocalDateTime>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<DateTimePicker, LocalDateTime> dateTimePickerLocalDateTimeComponentValueChangeEvent) {
                myTestService.setEndDate(dateTimePicker2.getValue(), testID);
            }
        });
        dateTimePicker.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DateTimePicker, LocalDateTime>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<DateTimePicker, LocalDateTime> dateTimePickerLocalDateTimeComponentValueChangeEvent) {
                myTestService.setStartDate(dateTimePicker.getValue(), testID);
            }
        });
        timePicker.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TimePicker, LocalTime>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<TimePicker, LocalTime> timePickerLocalTimeComponentValueChangeEvent) {
                myTestService.setTimer(timePicker.getValue(), testID);
            }
        });

    }
    public CreateTest(){
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        titleTest = new TextField("fasf", "Введите название теста");
        subtitleTest = new TextField();
        text = new TextArea();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        checkbox = new Checkbox();
        timePicker = new TimePicker();
        checkbox2 = new Checkbox();
        dateTimePicker = new DateTimePicker();
        dateTimePicker2 = new DateTimePicker();
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
        titleTest.setLabel("Название теста");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, titleTest);
        titleTest.setWidth("min-content");
        subtitleTest.setLabel("Подзаголовок");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, subtitleTest);
        subtitleTest.setWidth("min-content");
        text.setLabel("Описание");
        text.setWidth("300px");
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
        checkbox.setLabel("Вкл");
        checkbox.addClickListener((ComponentEventListener<ClickEvent<Checkbox>>) checkboxClickEvent -> {
            timePicker.setEnabled(!timePicker.isEnabled());
            timePicker.setValue(null);
        });
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, checkbox);
        checkbox.setWidth("min-content");
        timePicker.setLabel("Time picker");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, timePicker);
        timePicker.setWidth("min-content");
        checkbox2.setLabel("Вкл");
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
        layoutRow.add(titleTest);
        layoutRow.add(subtitleTest);
        layoutRow.add(text);
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
        layoutColumn4.add(layoutRow4);
        layoutRow4.getStyle().set("flex-grow", "1");

        addButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                Map<String, String> param = new HashMap<>();
                param.put("testID", String.valueOf(testID));
                param.put("createQ", String.valueOf(myTestService.createQuestion(testID, select.getValue().toString()).getId()));
                getUI().ifPresent(ui ->
                        ui.navigate(AdminView.class, new RouteParameters(param)));
            }
        });


        checkbox2.addClickListener((ComponentEventListener<ClickEvent<Checkbox>>) checkboxClickEvent -> {
            if(dateTimePicker.isEnabled()) {
                dateTimePicker.setEnabled(false);
                dateTimePicker2.setEnabled(false);
                dateTimePicker.setValue(null);
                dateTimePicker2.setValue(null);
            }
            else {
                dateTimePicker.setEnabled(true);
                dateTimePicker2.setEnabled(true);
            }
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
        select.setValue(sampleItems.get(0));
    }
    private Component createQComponent(Question question) {
        TextField tx = new TextField();
        tx.setReadOnly(true);
        tx.setValue(question.getText());
        Button redactionButton = new Button();
        redactionButton.setIcon(LumoIcon.EDIT.create());
        redactionButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                Map<String, String> param = new HashMap<>();
                param.put("testID", String.valueOf(testID));
                param.put("createQ", String.valueOf(question.getId()));
                getUI().ifPresent(ui -> ui.navigate(AdminView.class, new RouteParameters(param)));
            }
        });
//        TextField type = new TextField(question.getTypeQ().toString());
        final Button deleteButton = new Button(
                VaadinIcon.MINUS_CIRCLE_O.create(), event -> {

            // Ask for confirmation before deleting stuff
            final ConfirmDialog dialog = new ConfirmDialog(
                    "Подтверждение действия",
                    "Вы уверенны что хотите удалить этот вопрос?",
                    "Удалить", (e) -> {
//                DataService.get()
//                        .deleteCategory(category.getId());
                qDataProvider.getItems().remove(question);
                qDataProvider.refreshAll();
                newTestService.deleteAllData(question.getId());
                Notification.show("Вопрос удалён");
            });

            dialog.open();

        });

        final BeanValidationBinder<Question> binder = new BeanValidationBinder<>(
                Question.class);
        binder.forField(tx).bind("text");
//        binder.forField(type).bind("typeQ");
        binder.setBean(question);
        binder.addValueChangeListener(event -> {
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        final HorizontalLayout layout = new HorizontalLayout(tx,redactionButton, deleteButton);
        layout.setFlexGrow(1);
        return layout;
    }
    private Component createUserComponent(User user) {
        if(!myTestService.checkUser(user, myTestService.findTestByMytestId(testID))) {
            myTestService.addUserAvailableTest(user, myTestService.findTestByMytestId(testID));
        }
        TextField tx = new TextField();
        tx.setReadOnly(true);
        tx.setValue(user.getUsername());
        final Button deleteButton = new Button(
                VaadinIcon.MINUS_CIRCLE_O.create(), event -> {
                userDataProvider.getItems().remove(user);
                userDataProvider.refreshAll();
                myTestService.removeUserAvailableTest(user, myTestService.findTestByMytestId(testID));
                Notification.show("Пользователь удалён");
        });

        final BeanValidationBinder<User> binder = new BeanValidationBinder<>(
                User.class);
        binder.forField(tx).bind("username");
//        binder.forField(type).bind("typeQ");
        binder.setBean(user);
        binder.addValueChangeListener(event -> {
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        final HorizontalLayout layout = new HorizontalLayout(tx, deleteButton);
        layout.setFlexGrow(1);
        return layout;
    }
}