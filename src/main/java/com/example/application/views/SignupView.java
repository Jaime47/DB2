package com.example.application.views;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.UserService;
import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
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
	//Variable UI declaration
	//Text Fields
    private TextField userName = new TextField("Username");
    private TextField email = new TextField("Email");
    private TextField password = new TextField("Password");
    //Buttons
    private Button signUp = new Button("SignUp");
    private Button cancel = new Button("Cancel");
    //Binder to user entity
    private Binder<User> binder = new Binder<User>(User.class);
    
    
    
    
    // View construction
    public SignupView(UserService um) {
        addClassName("signup-view");
        add(createTitle("Sign Up"));
        add(createFormLayout(userName,email,password));
        add(createButtonLayout());
        binder.bindInstanceFields(this);
        clearForm();
       // Button interactivity
       cancel.addClickListener(e -> clearForm());
       signUp.addClickListener(e -> {
    	  
    	   um.createUser(binder.getBean());
    	   clearForm();
        });
    }
    //Clear form action
    private void clearForm() {
        binder.setBean(new User());
    }
    
    
    
    //View Design Oriented Functions
    
    
    //Function to create the UI title
    private Component createTitle(String title) {
        return new H3(title);
    }
    //Function to create the form layout to Sign Up
    private Component createFormLayout(TextField... fields) {
        FormLayout formLayout = new FormLayout();
        formLayout.add(fields);
        return formLayout;
    }
    // Button layout associated to view
    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        signUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(signUp);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

}