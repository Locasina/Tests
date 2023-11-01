package com.example.application.views.imagelist;

import com.example.application.views.tests.Test1;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.*;
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

    public TestListViewCard(String text, String title, String subTitle, String testID, int state, int id) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        addClickListener(e ->
                getUI().ifPresent(ui ->
                        ui.navigate(Test1.class, new RouteParameters("testID", String.valueOf(id))))
        );

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(subTitle);

        Paragraph description = new Paragraph(text);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();

        if(state == 0 ) {
            badge.getElement().setAttribute("theme", "badge");
            badge.setText("Not passed");
        }
        else if(state == 1) {
            badge.setText("Confirmed");
            badge.getElement().getThemeList().add("badge success");
        } else if (state == 2) {
            badge.setText("in progress");
            badge.getElement().getThemeList().add("badge contrast");
        } else {
            badge.getElement().setAttribute("theme", "badge");
            badge.setText("Passed");
        }

//        Span button = new Span("Go");
//        button.addClickListener(e ->
//                button.getUI().ifPresent(ui ->
//                        ui.navigate(testID))
//        );

        add( header, subtitle, description, badge);

    }
}
