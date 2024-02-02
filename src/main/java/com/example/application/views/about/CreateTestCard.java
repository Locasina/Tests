package com.example.application.views.about;

import com.example.application.views.tests.Test1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class CreateTestCard extends ListItem {

    public CreateTestCard(String text, String title, String subTitle, int state, int id, Button button) {
        if(id == 3)
            add(button);
        else {
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

            if (state == 0) {
                badge.getElement().setAttribute("theme", "badge");
                badge.setText("Not passed");
            } else if (state == 1) {
                badge.setText("Confirmed");
                badge.getElement().getThemeList().add("badge success");
            } else if (state == 2) {
                badge.setText("in progress");
                badge.getElement().getThemeList().add("badge contrast");
            } else {
                badge.getElement().setAttribute("theme", "badge");
                badge.setText("Passed");
            }

            add(header, subtitle, description, badge);
        }

    }
    public CreateTestCard(Button button){
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText("");

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("subTitle");

        Paragraph description = new Paragraph("text");
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();

        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Not passed");

        badge.setText("Confirmed");
        badge.getElement().getThemeList().add("badge success");
        add(button);
    }
}
