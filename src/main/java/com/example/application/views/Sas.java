package com.example.application.views;



import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;
import java.util.List;

@PageTitle("My View")
@Route(value = "my-view", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class Sas extends Composite<VerticalLayout> {

    public Sas() {
        MultiSelectListBox textItems = new MultiSelectListBox();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        textItems.setWidth("min-content");
        setMultiSelectListBoxSampleData(textItems);
        getContent().add(textItems);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setMultiSelectListBoxSampleData(MultiSelectListBox multiSelectListBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", null));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        multiSelectListBox.setItems(sampleItems);
        multiSelectListBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
        multiSelectListBox.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
}