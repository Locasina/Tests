package com.example.application.views;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Route("register")
@PageTitle("Register | site")
@AnonymousAllowed
public class Register extends VerticalLayout {
    @Autowired
    private UserRepository userRepository;

    private Checkbox allowMarketingBox;
    private PasswordField passwordField1;
    private PasswordField passwordField2;


    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    /**
     * We use Spring to inject the backend into our view
     */
    public Register() {

        /*
         * Create the components we'll need
         */

        H3 title = new H3("Signup form");

        TextField firstnameField = new TextField("First name");
        TextField lastnameField = new TextField("Last name");
        TextField handleField = new TextField("Username");

        // We'll need these fields later on so let's store them as class variables
        allowMarketingBox = new Checkbox("Are you gay?");
        allowMarketingBox.getStyle().set("padding-top", "10px");
//        EmailField emailField = new EmailField("Email");
//        emailField.setVisible(false);

        passwordField1 = new PasswordField("Wanted password");
        passwordField2 = new PasswordField("Password again");

        Span errorMessage = new Span();

        Button submitButton = new Button("Зарегестрироваться");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if(passwordField1.getValue().equals(passwordField2.getValue())&!userRepository.existsById(handleField.getValue())) {
                UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username(handleField.getValue())
                // password = password with this hash, don't tell anybody :-)
                .password(passwordField1.getValue())
                .roles("USER")
                .build();
                new InMemoryUserDetailsManager(user);
                User user1 = new User();
                user1.setUsername(user.getUsername());
                user1.setPassword(passwordField1.getValue());
                user1.setAccountNonLocked(true);
                userRepository.save(user1);
                UI.getCurrent().getPage().setLocation("login");
            }
        });
        /*
         * Build the visible layout
         */

        // Create a FormLayout with all our components. The FormLayout doesn't have any
        // logic (validation, etc.), but it allows us to configure Responsiveness from
        // Java code and its defaults looks nicer than just using a VerticalLayout.
        FormLayout formLayout = new FormLayout(title, firstnameField, lastnameField, handleField, passwordField1, passwordField2,
                allowMarketingBox, errorMessage, submitButton);

        // Restrict maximum width and center on page
        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components take full width regardless if we use one column or two (it
        // just looks better that way)
        formLayout.setColspan(title, 2);
        formLayout.setColspan(errorMessage, 2);
        formLayout.setColspan(submitButton, 2);

        // Add some styles to the error message to make it pop out
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        // Add the form to the page
        add(formLayout);

        /*
         * Set up form functionality
         */

        /*
         * Binder is a form utility class provided by Vaadin. Here, we use a specialized
         * version to gain access to automatic Bean Validation (JSR-303). We provide our
         * data class so that the Binder can read the validation definitions on that
         * class and create appropriate validators. The BeanValidationBinder can
         * automatically validate all JSR-303 definitions, meaning we can concentrate on
         * custom things such as the passwords in this class.
         */

    }
    private boolean CorrectPassword() {
        return true;
    }
}
