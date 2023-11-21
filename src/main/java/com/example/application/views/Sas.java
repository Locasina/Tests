package com.example.application.views;



import com.example.application.data.entity.OptionsMatching;
import com.example.application.data.entity.Question;
import com.example.application.data.repository.OptionsMatchingRepository;
import com.example.application.views.MainLayout;
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
@Route(value = "my-view", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class Sas extends Composite<VerticalLayout> implements BeforeEnterObserver {

    @Autowired
    OptionsMatchingRepository optionsMatchingRepository;
    OptionsMatching optionsMatching = new OptionsMatching();

    public Sas() {

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(optionsMatchingRepository.count()==0) {
            optionsMatching.setText("один");
            optionsMatchingRepository.save(optionsMatching);

            optionsMatching = new OptionsMatching();
            optionsMatching.setText("два");
            optionsMatchingRepository.save(optionsMatching);

            optionsMatching = new OptionsMatching();
            optionsMatching.setText("три");
            optionsMatchingRepository.save(optionsMatching);

            optionsMatching = new OptionsMatching();
            optionsMatching.setText("чатыре");
            optionsMatchingRepository.save(optionsMatching);



        }
        List<OptionsMatching> result =
                StreamSupport.stream(optionsMatchingRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());

        List<SampleItem> sampleItems = new ArrayList<>();

        sampleItems.add(new SampleItem(result.get(0).getText(), result.get(0).getText(), null));
        sampleItems.add(new SampleItem(result.get(1).getText(), result.get(1).getText(), null));
        sampleItems.add(new SampleItem(result.get(2).getText(), result.get(2).getText(), null));
        sampleItems.add(new SampleItem(result.get(3).getText(), result.get(3).getText(), null));

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