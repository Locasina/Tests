package com.example.application.views.about;

import com.example.application.data.entity.Test;
import com.example.application.data.repository.TestRepository;
import com.example.application.views.CreateTest;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@PermitAll
public class AboutView extends Main implements HasComponents, HasStyle, BeforeEnterObserver{
    Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
    @Autowired
    TestRepository testRepository;

    public AboutView() {
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.setAriaLabel("Add item");
        plusButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                Test test = new Test();
                List<Test> tests = testRepository.findAll();
                test.setId(tests.size()+1);
                test.setText("");
                test.setSubtitle("");
                test.setTitle("");
                testRepository.save(test);
                System.out.println(test.getId());
                getUI().ifPresent(ui -> ui.navigate(CreateTest.class, new RouteParameters("testID", String.valueOf(test.getId()))));
            }
        });


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

