package com.example.application.views.about;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@PermitAll
public class AboutView extends Main implements HasComponents, HasStyle, BeforeEnterObserver{
    Button plusButton = new Button(new Icon(VaadinIcon.PLUS));

    public AboutView() {
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.setAriaLabel("Add item");

        constructUI();

    }
    private OrderedList testCardContainer;

    private void constructUI() {
        addClassNames("image-list-view");
        addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Available tests");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, LumoUtility.FontSize.XXXLARGE);
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");

        testCardContainer = new OrderedList();
        testCardContainer.addClassNames(LumoUtility.Gap.MEDIUM, LumoUtility.Display.GRID, LumoUtility.ListStyleType.NONE, Margin.NONE, LumoUtility.Padding.NONE);

        container.add(headerContainer, sortBy);
        add(container, new VerticalLayout(plusButton), testCardContainer);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        testCardContainer.removeAll();
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
        testCardContainer.add(new CreateTestCard("1 тест", "Название теста", "Подзоголовок",3, 4));
    }
}

