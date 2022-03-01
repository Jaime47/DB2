package com.example.application.views.employee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.application.MyUserDetailsService;
import com.example.application.SessionService;
import com.example.application.data.entity.Employee;
import com.example.application.data.entity.User;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.ServicePackageRepository;
import com.example.application.data.repository.ServiceRepository;
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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Employee Log in")
@Route(value = "e-login")
@Uses(Icon.class)
public class eLoginView extends Div {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private Button logIn = new Button("LogIn");
    private Button cancel = new Button("Cancel");
    private Binder<Employee> binder = new Binder<Employee>(Employee.class);
    //private RouterLink successLink = new RouterLink();

    public eLoginView(HttpServletRequest request, EmployeeRepository ER) {
        addClassName("elogin-view");
        add(createTitle("Employee Log In"));
        add(createFormLayout(name,password));
        add(createButtonLayout());
        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        logIn.addClickListener(e -> {	
        	try {
            Employee.EmployeeLogIn(request, binder.getBean(), ER);
            if (request.getSession().getAttribute("User").equals(binder.getBean()))
                clearForm();
            	logIn.getUI().ifPresent(ui -> ui.navigate("e-home"));
        	}
        	
        	catch(Exception e1) {
                Notification.show("Invalid Login");
                clearForm();
        	}        
        });
    }

    private void clearForm() {
        binder.setBean(new Employee());
    }

    
    //View Design Oriented Functions
    
    
    private Component createTitle(String title) {
        return new H3(title);
    }

    private Component createFormLayout(TextField name, PasswordField password) {
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, password);
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