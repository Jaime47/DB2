package com.example.application.views.login;

import com.example.application.data.entity.User;

import com.example.application.data.service.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Sign Up")
@Route(value = "signup", layout = MainLayout.class)
@Uses(Icon.class)
public class SignupView extends Div {

    private TextField userName = new TextField("Username");
    private TextField email = new TextField("Email");
    private TextField password = new TextField("Password");

    private Button signUp = new Button("SignUp");
    private Button cancel = new Button("Cancel");

    private Binder<User> binder = new Binder(User.class);

    public SignupView(UserService userService) {
        addClassName("signup-view");
        add(createTitle("Sign Up"));
        add(createFormLayout(userName,email,password));
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        signUp.addClickListener(e -> {
            userService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new User());
    }

    private Component createTitle(String title) {
        return new H3(title);
    }

    private Component createFormLayout(TextField... fields) {
        FormLayout formLayout = new FormLayout();
        formLayout.add(fields);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        signUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(signUp);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

}