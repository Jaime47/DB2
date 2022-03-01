package com.example.application.views;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.application.MyUserDetailsService;
import com.example.application.ServiceService;
import com.example.application.SessionService;
import com.example.application.UserService;
import com.example.application.data.Service.FixedInternetService;
import com.example.application.data.Service.MobilePhoneService;
import com.example.application.data.Service.MyService;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.Service.ServiceType;
import com.example.application.data.entity.User;
import com.example.application.data.repository.ServicePackageRepository;
import com.example.application.data.repository.ServiceRepository;
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

@PageTitle("Log in")
@Route(value = "login", layout = MainLayout.class)
@Uses(Icon.class)
public class LoginView extends Div {

	
    private TextField userName = new TextField("Username");
    private TextField password = new TextField("Password");
    private Button logIn = new Button("LogIn");
    private Button cancel = new Button("Cancel");
    private Binder<User> binder = new Binder<User>(User.class);

    public LoginView(HttpServletRequest request, SessionService SS, MyUserDetailsService userDetails, ServiceRepository SP, ServicePackageRepository SPR) {
        addClassName("login-view");
        add(createTitle("Log In"));
        add(createFormLayout(userName,password));
        add(createButtonLayout());
        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        logIn.addClickListener(e -> {	
        	try {
            UserDetails UD = SS.logIn(request, userDetails, userName.getValue(), password.getValue());
            Notification.show("Succesfull Login " + UD.getUsername());

            clearForm();
        	}
        	catch(Exception e1) {
                Notification.show("Invalid Login");
                clearForm();
        	}        
        });
    }

    private void clearForm() {
        binder.setBean(new User());
    }

    
    //View Design Oriented Functions
    
    
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
        logIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(logIn); 
        buttonLayout.add(cancel);
        return buttonLayout;
    }

}
