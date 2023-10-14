package com.example.application.views.imagelist;

import com.example.application.views.tests.Test1;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class TestListViewCard extends ListItem {
    boolean fl = false;


    public void BeforeLeaveEvent(BeforeEnterEvent beforeEnterEvent) {
        if(fl)
            beforeEnterEvent.forwardTo(Test1.class);
    }


    public TestListViewCard(String text, String title, String subTitle) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);



        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(subTitle);

        Paragraph description = new Paragraph(text);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Pass");
        Span button = new Span("Go");
        button.getElement().setAttribute("theme", "button");
        button.addClickListener(e ->
                button.getUI().ifPresent(ui ->
                        ui.navigate("test1"))
        );

        add( header, subtitle, description, badge, button);

    }
}
