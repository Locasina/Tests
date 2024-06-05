package com.example.application.views.testlist;

import com.example.application.views.tests.Test1;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class TestListViewCard extends ListItem {

    public TestListViewCard(String text, String title, String subTitle, int state, int id) {
        if(state == -1) {

        }else {
            addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                    BorderRadius.LARGE);
            if (state == 0)
            addClickListener(e ->
                    getUI().ifPresent(ui ->
                            ui.navigate(PreviewPage.class, new RouteParameters("testID", String.valueOf(id))))
            );
            else
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

            if (state == 0) {
                badge.getElement().setAttribute("theme", "badge");
                badge.setText("Не пройден");
            } else if (state == 1) {
                badge.setText("Завершён");
                badge.getElement().getThemeList().add("badge success");
            } else if (state == 2) {
                badge.setText("Проходится");
                badge.getElement().getThemeList().add("badge contrast");
            } else {
                badge.getElement().setAttribute("theme", "badge");
                badge.setText("Passed");
            }

            add(header, subtitle, description, badge);
        }

    }
}
