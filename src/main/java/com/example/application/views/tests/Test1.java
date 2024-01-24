package com.example.application.views.tests;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.QuestionRepository;
import com.example.application.data.repository.TestRepository;
import com.example.application.security.SecurityService;
import com.example.application.views.MainLayout;
import com.example.application.views.imagelist.TestSupport;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
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
public class Test1 extends Composite<VerticalLayout> implements BeforeEnterObserver {

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
    private String option;
    protected int i;
    private int numberOfQ;
    private String testID;
    private H1 h1 = new H1();
    protected H1 h2 = new H1();
    private HorizontalLayout layoutRow = new HorizontalLayout();
    private Button nextButton = new Button();
    private Button previousButton = new Button();
    private HorizontalLayout layoutRow2 = new HorizontalLayout();

    protected Map <Integer, String> chosenOptions = new HashMap<>();
//    TestSupport t1 = new TestSupport();
//    Thread thread = new Thread(t1);


    private RadioButtonGroup radioGroup = new RadioButtonGroup();
    private CheckboxGroup checkboxGroup = new CheckboxGroup();
    private TextField textField = new TextField();




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

        numberOfQ = questions.size();

        update();



    }


    public Test1() {

        getContent().setHeightFull();
        getContent().setWidthFull();
//        thread.start();


        radioGroup.addValueChangeListener(event -> {
            System.out.println("VCL worked");
            if(event.getValue()!= null){
                option =  event.getValue().toString();
                System.out.println("VCL's (if) just worked");
            }
        });

        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i < numberOfQ) {
                if (option != null) {
                    System.out.println("nxtbut listener");
                    chosenOptions.put(i, option);
                    System.out.println("value's saved and cleared");
                    option = null;

                }
                i++;
                System.out.println("i raised");

                setQuestion();

            }
            h1.setText(i + "/" + numberOfQ);
        });

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i>1) {
                if (option != null) {
                    System.out.println("prevbut listener");
                    chosenOptions.put(i, option);
                    System.out.println("value's saved and cleared");
                    option = null;
                }
                i--;
                System.out.println("i decreased");

                setQuestion();

            }
            h1.setText(i + "/" + numberOfQ);
        });

    }
    private void setQuestion(){
        System.out.println("setAnswer goes");
        setAnswers(questions.get(i-1).getTypeQ());
        choiceNotifier();

    }
    private void choiceNotifier (){
        if(chosenOptions.get(i)!=null){
            System.out.println("ATTENTION! Value is setting");
            radioGroup.setValue(chosenOptions.get(i));

        }

    }

    private void update(){
        h1.setText(i + "/" + numberOfQ);
        setAnswers(questions.get(i-1).getTypeQ());
        h2.setText(chosenOptions.get(i));


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
        getContent().add(h2);


    }
    private void setAnswers(int n) {
        if (n == 1) {
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(true);
            textField.setVisible(false);
            radioGroup.setLabel(questions.get(i - 1).getText());
            System.out.println("set items is working");
            radioGroup.setItems(answers.get(i - 1));
        }
        if(n == 2) {
            checkboxGroup.setVisible(true);
            radioGroup.setVisible(false);
            textField.setVisible(false);
            checkboxGroup.setLabel(questions.get(i - 1).getText());
            checkboxGroup.setItems(answers.get(i - 1));
            System.out.println("set items is working");
        }
        if(n == 3) {
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(false);
            textField.setVisible(true);
            textField.setLabel(questions.get(i - 1).getText());
        }
        if(n ==4){
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(false);
            textField.setVisible(false);


        }
    }






}
