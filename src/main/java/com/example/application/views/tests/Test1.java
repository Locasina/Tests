package com.example.application.views.tests;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.ComparisonAnswer;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.ComparisonAnswerRepository;
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
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
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
    private ComparisonAnswerRepository comparisonAnswerRepository;
    List<Question> questions;
    List<List<Answer>> answers;
    private String option;

    protected int i;
    private int numberOfQ;
    private String testID;
    private H1 h1 = new H1();
    private H2 h2 = new H2();
    private H4 h4 = new H4();
    private HorizontalLayout layoutRow = new HorizontalLayout();
    private HorizontalLayout layoutRow2 = new HorizontalLayout();
    private HorizontalLayout answersLayout = new HorizontalLayout();
    private Button nextButton = new Button();
    private Button previousButton = new Button();
    protected Map <Integer, String> chosenOptions = new HashMap<>();
    private RadioButtonGroup radioGroup = new RadioButtonGroup();
    private CheckboxGroup checkboxGroup = new CheckboxGroup();
    private TextField textField = new TextField();
    private Grid<ComparisonAnswer> grid = new Grid<>(ComparisonAnswer.class, false);
    private Grid<ComparisonAnswer> grid2 = new Grid<>(ComparisonAnswer.class, false);
    private Map<Integer, List<ComparisonAnswer>> compAnswers;


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        i = 1;
        int j =1;


        testID =beforeEnterEvent.getRouteParameters().get("testID").get();

        questions =
                StreamSupport.stream(questionRepository.findByTestId(Integer.parseInt(testID)).spliterator(), false)
                        .collect(Collectors.toList());
        answers = new ArrayList<>();
        compAnswers = new HashMap<>();
        for(Question q: questions){
            if(q.getTypeQ()==4) {
                compAnswers.put(j, comparisonAnswerRepository.findByQuestionId(q.getId()));
            } else {
                answers.add(answerRepository.findByQuestionId(q.getId()));
                compAnswers.put(j, null);
            }
            j++;
        }

        numberOfQ = questions.size();

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
                if (option != null) {
                    chosenOptions.put(i, option);
                    option = null;

                }
                i++;
                h4.setText(null);
                setQuestion();

            }
            h1.setText(i + "/" + numberOfQ);
        });

        previousButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (i>1) {
                if (option != null) {
                    chosenOptions.put(i, option);
                    option = null;
                }
                i--;
                h4.setText(null);
                setQuestion();

            }
            h1.setText(i + "/" + numberOfQ);

        });

    }
    private void setQuestion(){
        setAnswers(questions.get(i-1).getTypeQ());
        choiceNotifier();

    }
    private void choiceNotifier (){
        if(chosenOptions.get(i)!=null){
            radioGroup.setValue(chosenOptions.get(i));

        }

    }

    private void update(){
        h1.setText(i + "/" + numberOfQ);
        setAnswers(questions.get(i-1).getTypeQ());
        h2.setText(questions.get(i - 1).getText());

        grid.setAllRowsVisible(true);
        grid2.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid2.setSelectionMode(Grid.SelectionMode.NONE);


        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        answersLayout.setWidth("50%");

        nextButton.setText("next");
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousButton.setText("previous");
        previousButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.add(h4);

        getContent().add(h1);
        getContent().add(h2);


        answersLayout.add(radioGroup);
        answersLayout.add(checkboxGroup);
        answersLayout.add(textField);
        answersLayout.add(grid);
        answersLayout.add(grid2);

        getContent().add(answersLayout);
        getContent().add(layoutRow);
        layoutRow.add(previousButton);
        layoutRow.add(nextButton);
        getContent().add(layoutRow2);



    }
    private void setAnswers(int n) {
        if (n == 1) {
            checkboxGroup.setVisible(false);
            textField.setVisible(false);
            grid.setVisible(false);
            grid2.setVisible(false);
            radioGroup.setVisible(true);
//            radioGroup.setLabel(questions.get(i - 1).getText());
            h2.setText(questions.get(i - 1).getText());

            radioGroup.setItems(answers.get(i - 1));
        }
        if(n == 2) {
            radioGroup.setVisible(false);
            textField.setVisible(false);
            grid.setVisible(false);
            grid2.setVisible(false);
            checkboxGroup.setVisible(true);
//            checkboxGroup.setLabel(questions.get(i - 1).getText());
            checkboxGroup.setItems(answers.get(i - 1));
            h2.setText(questions.get(i - 1).getText());

        }
        if(n == 3) {
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(false);
            grid.setVisible(false);
            grid2.setVisible(false);
            textField.setVisible(true);
//          textField.setLabel(questions.get(i - 1).getText());
            h2.setText(questions.get(i - 1).getText());
        }
        if(n ==4){
            checkboxGroup.setVisible(false);
            radioGroup.setVisible(false);
            textField.setVisible(false);
            grid.addColumn(ComparisonAnswer::getColumn1);
            grid2.addColumn(ComparisonAnswer::getColumn2);
            grid.setVisible(true);
            grid2.setVisible(true);
            grid.setItems(compAnswers.get(i));
            grid2.setItems(compAnswers.get(i));
            h2.setText(questions.get(i - 1).getText());
            h4.setText("First column is reordering. Reorder its items to match ones in the second column ");  //Пофиксить

        }
    }

//    public List<String> gridFiller1(){
//        List<String> column = new ArrayList<>();
//        for(List<ComparisonAnswer> element : compAnswers){
//            for(ComparisonAnswer comparisonAnswer : element){
//                column.add(comparisonAnswer.getColumn1());
//            }
//        }                                                        //ПОЛНОЕ
//        return column;                                           //УБЛЮДСТВО
//    }
//    private List<String> gridFiller2(){
//        List<String> column = new ArrayList<>();                  //НАДО
//        for(List<ComparisonAnswer> element : compAnswers){
//            for (ComparisonAnswer comparisonAnswer : element){
//                column.add(comparisonAnswer.getColumn2());
//            }
//        }                                                         //ПОФИКСИТЬ
//        return column;
//    }

}
