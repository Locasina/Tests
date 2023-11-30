package com.example.application.views;



import com.example.application.data.entity.Answer;
import com.example.application.data.entity.MultiChoiceAnswer;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.AnswerRepository;
import com.example.application.data.repository.MultiChoiceAnswerRepository;
import com.example.application.data.repository.QuestionRepository;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@PageTitle("My View")
@Route(value = "sas", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class Sas extends Composite<VerticalLayout> implements BeforeEnterObserver {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    MultiChoiceAnswerRepository mcaRepo;

    public Sas() {

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {


//        List<Answer> result =
//                StreamSupport.stream(answerRepository.findAll().spliterator(), false)
//                        .collect(Collectors.toList());



        List<SampleItem> sampleItems = new ArrayList<>();

//        sampleItems.add(new SampleItem(result.get(0).getText(), result.get(0).getText(), null));
//        sampleItems.add(new SampleItem(result.get(1).getText(), result.get(1).getText(), null));
//        sampleItems.add(new SampleItem(result.get(2).getText(), result.get(2).getText(), null));
//        sampleItems.add(new SampleItem(result.get(3).getText(), result.get(3).getText(), null));

        MultiSelectListBox textItems = new MultiSelectListBox();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        textItems.setWidth("min-content");
        textItems.setItems(sampleItems);
        textItems.setItemLabelGenerator(item -> ((SampleItem) item).label());
        textItems.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
        getContent().add(textItems);




    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setMultiSelectListBoxSampleData(MultiSelectListBox multiSelectListBox) {


    }
}