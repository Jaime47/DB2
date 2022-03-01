package com.example.application.views;

import com.example.application.MyUserDetailsService;
import com.example.application.OrderService;
import com.example.application.SessionService;
import com.example.application.UserService;
import com.example.application.data.Order;
import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.entity.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@PageTitle("Checkout Form")
@Route(value = "checkout-form", layout = MainLayout.class)
public class CheckOutFormView extends Div {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
    public CheckOutFormView(HttpServletRequest request, OrderService OS, UserService US, SessionService SS, MyUserDetailsService UDS) {
        addClassNames("checkout-form-view", "flex", "flex-col", "h-full");

        Main content = new Main();
        content.addClassNames("grid", "gap-xl", "items-start", "justify-center", "max-w-screen-md", "mx-auto", "pb-l",
                "px-l");

        content.add(createCheckoutForm(request, OS, US, SS, UDS));
        content.add(createAside(request, OS));
        add(content);
    }

    private Component createCheckoutForm(HttpServletRequest request, OrderService OS, UserService US, SessionService SS, MyUserDetailsService UDS) {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames("flex", "flex-col", "flex-grow");

        H2 header = new H2("Checkout");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph note = new Paragraph("All fields are required unless otherwise noted");
        note.addClassNames("mb-xl", "mt-0", "text-secondary");
        checkoutForm.add(header, note);

        checkoutForm.add(createPaymentInformationSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter(request, OS, US, SS, UDS));
        
        
        

        return checkoutForm;
    }



    private Component createPaymentInformationSection() {
        Section paymentInfo = new Section();
        paymentInfo.addClassNames("flex", "flex-col", "mb-xl", "mt-m");

        H3 header = new H3("Payment details");
        header.addClassNames("mb-m", "mt-s", "text-2xl");

        TextField cardHolder = new TextField("Cardholder name");
        cardHolder.setRequiredIndicatorVisible(true);
        cardHolder.setPattern("[\\p{L} \\-]+");
        cardHolder.addClassNames("mb-s");

        Div subSectionOne = new Div();
        subSectionOne.addClassNames("flex", "flex-wrap", "gap-m");

        TextField cardNumber = new TextField("Card Number");
        cardNumber.setRequiredIndicatorVisible(true);
        cardNumber.setPattern("[\\d ]{12,23}");
        cardNumber.addClassNames("mb-s");

        TextField securityCode = new TextField("Security Code");
        securityCode.setRequiredIndicatorVisible(true);
        securityCode.setPattern("[0-9]{3,4}");
        securityCode.addClassNames("flex-grow", "mb-s");
        securityCode.setHelperText("What is this?");

        subSectionOne.add(cardNumber, securityCode);

        Div subSectionTwo = new Div();
        subSectionTwo.addClassNames("flex", "flex-wrap", "gap-m");

        Select<String> expirationMonth = new Select<>();
        expirationMonth.setLabel("Expiration month");
        expirationMonth.setRequiredIndicatorVisible(true);
        expirationMonth.setItems("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        Select<String> expirationYear = new Select<>();
        expirationYear.setLabel("Expiration year");
        expirationYear.setRequiredIndicatorVisible(true);
        expirationYear.setItems("22", "23", "24", "25", "26");

        subSectionTwo.add(expirationMonth, expirationYear);

        paymentInfo.add( header, cardHolder, subSectionTwo);
        return paymentInfo;
    }

    private Footer createFooter(HttpServletRequest request, OrderService OS, UserService US, SessionService SS, MyUserDetailsService UDS) {
        Footer footer = new Footer();
    	Button login = new Button("Log In");
    	Dialog dialogNoUser = new Dialog();
        footer.addClassNames("flex", "items-center", "justify-between", "my-m");

        Button cancel = new Button("Cancel order");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickListener(e -> request.getSession().removeAttribute("Order"));
        cancel.addClickListener(e -> cancel.getUI().ifPresent(ui -> ui.navigate("home")));
        login.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        
        if (request.getSession().getAttribute("User") != null) {
            Button pay = new Button("Pay securely", new Icon(VaadinIcon.LOCK));
            pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

            
            Order currentOrder = (Order) request.getSession().getAttribute("Order");
            if (currentOrder != null) {
    			pay.addClickListener(e -> OS.transactionProcces(request, currentOrder, US));
    			pay.addClickListener(e -> pay.getUI().ifPresent(ui -> {ui.navigate("home");}));
            }
	        footer.add(cancel, pay);
        }
        else {
        	Button logInSignUp = new Button("Log in/ Sign Up", new Icon(VaadinIcon.LOCK));
        	logInSignUp.addClickListener(e -> {dialogNoUser.removeAll();dialogNoUser.add(notRegisteredDialogLayout(request,US,SS,UDS,login));
        	dialogNoUser.open();});
        	login.addClickListener(e->dialogNoUser.close());
        	login.addClickListener(e->UI.getCurrent().getPage().reload());
        	footer.add(cancel, logInSignUp);
        }
        
        

        return footer;
    }

    private Aside createAside(HttpServletRequest request, OrderService OS) {
        Aside aside = new Aside();
        aside.addClassNames("bg-contrast-5", "box-border", "p-l", "rounded-l", "sticky");
        Header headerSection = new Header();
        headerSection.addClassNames("flex", "items-center", "justify-between", "mb-m");
        H3 header = new H3("Order");
        header.addClassNames("m-0");
        Button edit = new Button("Edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        
        edit.addClickListener(e -> edit.getUI().ifPresent(ui -> ui.navigate("buyservice")));
        
        headerSection.add(header, edit);

        UnorderedList ul = new UnorderedList();
        ul.addClassNames("list-none", "m-0", "p-0", "flex", "flex-col", "gap-m");
        
        Order currentOrder = (Order) request.getSession().getAttribute("Order");
        
        if (currentOrder != null){ 

            ul.add(createListItem("Your selected package: ",currentOrder.getServPack().getPackageName()));
            currentOrder.getOptionalProducts().forEach(t -> ul.add(createListItem("Optional product: ",t.getName())));
            OS.calculatePrice(currentOrder);
            ul.add("\n" + "Total price: " + currentOrder.getPrice());
            ul.add(currentOrder.getStartDate().toString());
            	
        }
        


        aside.add(headerSection, ul);
        return aside;
    }
    private ListItem createListItem(String primary, String secondary) {
        ListItem item = new ListItem();
        item.addClassNames("flex", "justify-between");

        Div subSection = new Div();
        subSection.addClassNames("flex", "flex-col");

        subSection.add(new Span(primary));
        Span secondarySpan = new Span(secondary);
        secondarySpan.addClassNames("text-s text-secondary");
        subSection.add(secondarySpan);

        item.add(subSection);
        return item;
    }
    
 private VerticalLayout notRegisteredDialogLayout(HttpServletRequest request,UserService US, SessionService SS, MyUserDetailsService UDS, Button login) {
    	VerticalLayout popupContent = new VerticalLayout();
    	TextField username = new TextField("Username");
    	TextField password = new TextField("Password");

    	TextField email = new TextField("Email");
    	Button signUp = new Button("Sign Up");
    	popupContent.add(username, password, login, email, signUp);
    	
    	login.addClickListener(e -> SS.logIn(request, UDS, username.getValue(), password.getValue()));

    	
    	signUp.addClickListener(e -> US.createUser(username.getValue(),email.getValue(),password.getValue()));


    	
    	return popupContent;
    }
}
