package com.example.application.views.tests;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.QuestionRepository;
import com.example.application.data.repository.TestRepository;
import com.example.application.security.SecurityService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@PageTitle("test")
@PermitAll
@Route(value = "test/:testID", layout = MainLayout.class)
public class Test1 extends Composite<VerticalLayout> implements BeforeEnterObserver{

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    SecurityService securityService;
    @Autowired
    private TestRepository testRepository;
    List<Question> questions;
    List<List<Answer>> answers;




    ComponentController cc;

    private String testID;

    H1 h1 = new H1();

    HorizontalLayout layoutRow = new HorizontalLayout();
    Button nextButton = new Button();
    Button previousButton = new Button();
    HorizontalLayout layoutRow2 = new HorizontalLayout();

    Map <Integer, String> chosenOptions = new HashMap<>();
     String option;
     int i;
     int numberOfQ;

    RadioButtonGroup radioGroup = new RadioButtonGroup();
    CheckboxGroup checkboxGroup = new CheckboxGroup();
    TextField textField = new TextField();

    MultiSelectListBox optionsColumn1 = new MultiSelectListBox();

    MultiSelectListBox optionsColumn2 = new MultiSelectListBox();

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        i = 1;

        testID =beforeEnterEvent.getRouteParameters().get("testID").get();

        questions =
                StreamSupport.stream(questionRepository.findByTestId(Integer.parseInt(testID)).spliterator(), false)
                        .collect(Collectors.toList());
        answers = new ArrayList<>();
        for(Question q: questions){
            answers.add(answerRepository.findByQuestionId(q.getId()));
        }

        System.out.println(questions);

        numberOfQ = questions.size();
        System.out.println(questions.get(0).getAnswers().toString());

        update();
    }


    public Test1() {

        getContent().setHeightFull();
        getContent().setWidthFull();


        radioGroup.addValueChangeListener(event -> {
            if(event.getValue()!= null){
                option =  event.getValue().toString();
            }
        });

        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i < numberOfQ) {
                if (option != null)
                    chosenOptions.put(i, option);
                i++;

                nextQuestion();
                option = null;
            }
            h1.setText(i + "/" + numberOfQ);
        });

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i>1) {
                if (option != null)
                    chosenOptions.put(i, option);
                i--;

                previousQuestion();
                option = null;
            }
            h1.setText(i + "/" + numberOfQ);
        });

    }
    private void nextQuestion(){
//        if (questions.get(i-1).getTypeQ() == 1) {
//            checkboxGroup.setVisible(false);
//            radioGroup.setVisible(true);
//            radioGroup.setLabel(questions.get(i - 1).getText());
//            radioGroup.setItems(answers.get(i - 1));
//        }
//        if(questions.get(i-1).getTypeQ() == 2) {
//            checkboxGroup.setVisible(true);
//            radioGroup.setVisible(false);
//            checkboxGroup.setLabel(questions.get(i - 1).getText());
//            checkboxGroup.setItems(answers.get(i - 1));
//        }
//        if(questions.get(i-1).getTypeQ() == 3) {
//            checkboxGroup.setVisible(false);
//            radioGroup.setVisible(false);
//            textField.setVisible(true);
//        }
        setAnswer(questions.get(i-1).getTypeQ());
        choiceNotifier();
    }

    private void previousQuestion(){
//        if (questions.get(i-1).getTypeQ() == 1) { //вынести это в отдельный метод
//            checkboxGroup.setVisible(false);
//            radioGroup.setVisible(true);
//            radioGroup.setLabel(questions.get(i - 1).getText());
//            radioGroup.setItems(answers.get(i - 1));
//        }
//        if(questions.get(i-1).getTypeQ() == 2) {
//            checkboxGroup.setVisible(true);
//            radioGroup.setVisible(false);
//            checkboxGroup.setLabel(questions.get(i - 1).getText());
//            checkboxGroup.setItems(answers.get(i - 1));
//        }
        setAnswer(questions.get(i-1).getTypeQ());
        choiceNotifier();

    }
    private void choiceNotifier (){
        if(chosenOptions.get(i)!=null){
            radioGroup.setValue(chosenOptions.get(i));
        }

    }

    private void update(){
        h1.setText(i + "/" + numberOfQ);
        setAnswer(questions.get(i-1).getTypeQ());
//        if(questions.get(i-1).getTypeQ() == 1) {
//            checkboxGroup.setVisible(false);
//            radioGroup.setVisible(true);
//            radioGroup.setLabel(questions.get(i - 1).getText());
//            radioGroup.setItems(answers.get(i - 1));
//            radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
//        }
//
////        checkboxGroup.setWidth("min-content");
//
//        if(questions.get(i-1).getTypeQ() == 2) {
//        checkboxGroup.setVisible(true);
//        radioGroup.setVisible(false);
//        checkboxGroup.setLabel(questions.get(i-1).getText());
//        checkboxGroup.setItems((answers.get(i-1)));
//        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//        }

        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
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
        getContent().add(checkboxGroup);
        getContent().add(textField);

        getContent().add(layoutRow);
        layoutRow.add(previousButton);
        layoutRow.add(nextButton);
        getContent().add(layoutRow2);

    }
    private void setAnswer (int n) {
        if (n == 1) {
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(true);
            textField.setVisible(false);
            radioGroup.setLabel(questions.get(i - 1).getText());
            radioGroup.setItems(answers.get(i - 1));
        }
        if(n == 2) {
            checkboxGroup.setVisible(true);
            radioGroup.setVisible(false);
            textField.setVisible(false);
            checkboxGroup.setLabel(questions.get(i - 1).getText());
            checkboxGroup.setItems(answers.get(i - 1));
        }
        if(n == 3) {
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(false);
            textField.setVisible(true);
            textField.setLabel(questions.get(i - 1).getText());
        }
    }




}
