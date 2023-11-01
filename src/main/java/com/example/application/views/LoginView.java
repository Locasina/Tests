package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.imagelist.TestsListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@RouteAlias(value = "")
@PageTitle("Login | Tests")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();
    private SecurityService securityService;


    public LoginView(@Autowired SecurityService securityService){
        this.securityService = securityService;
        if (securityService.getAuthenticatedUser() != null) {
            UI.getCurrent().getPage().setLocation("tests-list");
        }
        else {
            addClassName("login-view");
            setSizeFull();
            setAlignItems(Alignment.CENTER);
            setJustifyContentMode(JustifyContentMode.CENTER);

            login.setAction("login");

            add(new H1("Vaadin CRM"), login);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}