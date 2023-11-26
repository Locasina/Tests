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
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@PageTitle("test")
@PermitAll
@Route(value = "test/:testID", layout = MainLayout.class)
public class Test1 extends Composite<VerticalLayout> implements BeforeEnterObserver{

    @Autowired
    SecurityService securityService;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

//    static Map<String, List<String>> parametersMap;

    ComponentController cc;

    private String testID;

    H1 h1 = new H1();

    HorizontalLayout layoutRow = new HorizontalLayout();
    Button nextButton = new Button();
    Button previousButton = new Button();
    HorizontalLayout layoutRow2 = new HorizontalLayout();

    Map <Integer, String> chosenOptions = new HashMap<>();
    List<Question> questions;
    List<Answer> answers;

     String option;
     int i;
     int numberOfQ;

    RadioButtonGroup radioGroup = new RadioButtonGroup();

    MultiSelectListBox optionsColumn1 = new MultiSelectListBox();

    MultiSelectListBox optionsColumn2 = new MultiSelectListBox();

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        i = 1;
        testID = beforeEnterEvent.getRouteParameters().get("testID").get();
        Answer as = new Answer();
        as.setQuestionId(1);
         questions =
                StreamSupport.stream(questionRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
         answers =
                StreamSupport.stream(answerRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        System.out.println(questions);
        System.out.println(answers);

        cc = new ComponentController(questions, answers, testID);
        System.out.println(cc.questionsText);
        System.out.println(cc.answers);              //cb посредник, надо куда-то сдыбрить код из него
        numberOfQ = cc.questionsText.size();
        update();
        System.out.println(cc.answers.get(2));

    }



    public Test1() {



        getContent().setHeightFull();
        getContent().setWidthFull();


        radioGroup.addValueChangeListener(event -> {
            if(event.getValue()!= null){
                option = (String) event.getValue();
            }
        });

        nextButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i < numberOfQ) {
                if (option != null)
                    chosenOptions.put(i, option);
                i++;

                nextQuestion(radioGroup);
                option = null;
            }
            h1.setText(i + "/" + numberOfQ);
        });

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i>1) {
                if (option != null)
                    chosenOptions.put(i, option);
                i--;
                previousQuestion(radioGroup);
                option = null;
            }
            h1.setText(i + "/" + numberOfQ);
        });

    }
    private void nextQuestion(RadioButtonGroup radioGroup){                 //дб не с АРГ, а с проверкой на typeQ
        radioGroup.setLabel(cc.questionsText.get(i-1));
        radioGroup.setItems(cc.answers.get(i));
        choiceNotifier(radioGroup);
        System.out.println(questions.get(i-1).getTypeQ());
    }

    private void previousQuestion(RadioButtonGroup radioGroup){             //дб не с АРГ, а с проверкой на typeQ
        radioGroup.setLabel(cc.questionsText.get(i-1));
        radioGroup.setItems(cc.answers.get(i));
        choiceNotifier(radioGroup);

    }
    private void choiceNotifier (RadioButtonGroup radioButtonGroup){
        if(chosenOptions.get(i)!=null){
            radioButtonGroup.setValue(chosenOptions.get(i));
        }

    }

    private void update(){
        h1.setText(i + "/" + numberOfQ);                                          //           ЗАВ
        radioGroup.setLabel(cc.questionsText.get(i-1));                               //
        radioGroup.setItems(cc.answers.get(i));                                   //            ОТ
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);             //
        layoutRow.setWidthFull();                                                 //           typeQ
        getContent().setFlexGrow(1.0, layoutRow);                         //
        layoutRow.addClassName(Gap.MEDIUM);                                       //

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
    }



}
