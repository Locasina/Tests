package com.example.application.views.imagelist;

import com.example.application.data.entity.AvailableTest;
import com.example.application.data.repository.AvailableTestRepository;
import com.example.application.security.SecurityService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@PermitAll
@PageTitle("Tests List")
@Route(value = "tests-list", layout = MainLayout.class)
public class TestsListView extends Main implements HasComponents, HasStyle , BeforeEnterObserver {
    private OrderedList testCardContainer;
    @Autowired
    AvailableTestRepository availableTestRepository;
    @Autowired
    SecurityService securityService;
    public TestsListView() {
        constructUI();
    }

    private void constructUI() {
        addClassNames("image-list-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Available tests");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");

        testCardContainer = new OrderedList();
        testCardContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer, sortBy);
        add(container, testCardContainer);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        testCardContainer.removeAll();

        List<AvailableTest> e = availableTestRepository.findByUserUsername(securityService.getAuthenticatedUser().getUsername());
        e.forEach(x -> testCardContainer.add(new TestListViewCard(x.getTest().
                getText(), x.getTest().getTitle(), x.getTest()
                .getSubtitle(), x.getState(), x.getTest().getId())));
    }
}
