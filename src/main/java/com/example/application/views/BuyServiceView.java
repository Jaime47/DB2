package com.example.application.views;


import com.example.application.MyUserDetails;
import com.example.application.data.Order;
import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.entity.User;
import com.example.application.data.repository.OptionalProductRepository;
import com.example.application.data.repository.ServicePackageRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@PageTitle("Buy Service")
@Route(value = "buyservice", layout = MainLayout.class)
public class BuyServiceView extends Div implements BeforeEnterObserver {

    private final String SERVICEPACKAGE_ID = "servicePackageID";

    private Grid<ServicePackage> packageGrid = new Grid<>(ServicePackage.class, false);
    private Grid<OptionalProduct> optionalProdGrid = new Grid<>(OptionalProduct.class, false);
    private Dialog dialogConfirm = new Dialog();
    private Dialog dialogAddOpt = new Dialog();
    private Button confirm = new Button("Confirm");
    private Button changeView = new Button("Change view");
    private Binder<Order> binder = new Binder<Order>(Order.class);
    //date picker!!!
    DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();

    DatePicker singleFormatDatePicker = new DatePicker("Select a date:");
    


    public BuyServiceView(HttpServletRequest request, ServicePackageRepository SPR, OptionalProductRepository OPR) {
        addClassNames("buyservice-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createPackageGridLayout(splitLayout);
        createOptProdLayout(splitLayout);
        add(splitLayout);
        
        
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.add(changeView); 
        
        
        Order prevOrder = (Order) request.getSession().getAttribute("Order");
        if (prevOrder != null) {
        	binder.setBean(prevOrder);
        }else {
        	binder.setBean(new Order());
        }
        
        
        changeView.addClickListener(e -> 
        request.getSession().setAttribute("Order", binder.getBean()));
        
        
        changeView.addClickListener(e ->
        changeView.getUI().ifPresent(ui ->
        ui.navigate("checkout-form")));
        add(buttonLayout);

        
        
        
        //Adding grids to application
        packageGrid.addColumn("packageName").setAutoWidth(true);	
		packageGrid.setItems(SPR.findAll());
		packageGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		packageGrid.setHeightFull();
		packageGrid.addItemDoubleClickListener(e -> {dialogConfirm.removeAll();dialogConfirm.add(confirmationLayout(e.getItem()));dialogConfirm.open();});
		confirm.addClickListener(e -> {dialogConfirm.close();});
		optionalProdGrid.addColumn("name").setAutoWidth(true);	
		optionalProdGrid.setItems(OPR.findAll());
		optionalProdGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		optionalProdGrid.setHeightFull();
		optionalProdGrid.addItemDoubleClickListener(e -> {dialogAddOpt.removeAll();dialogAddOpt.add(optAddLayout(e.getItem()));
		dialogAddOpt.open();});
		confirm.addClickListener(e -> {dialogAddOpt.close();});
    }

    
    private void createPackageGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        wrapper.add(new H3("Service Packages"));
        splitLayout.addToPrimary(wrapper);
        wrapper.add(packageGrid);
    }
    
    private void createOptProdLayout(SplitLayout splitLayout) {
        Div optProdWrapper = new Div();
        optProdWrapper.setId("rebuy-wrapper");
        optProdWrapper.setWidthFull();
        optProdWrapper.add(new H3("Optional Products"));
        splitLayout.addToSecondary(optProdWrapper);
        optProdWrapper.add(optionalProdGrid);
    }


    private void refreshGrid() {
        packageGrid.select(null);
        packageGrid.getDataProvider().refreshAll();
    }
    
    private void clearForm() {
        binder.setBean(new Order());
    }

    

    // Pop up CONFIRMATION
    
    private VerticalLayout confirmationLayout(ServicePackage SP) {
    	VerticalLayout popupContent = new VerticalLayout();
    	popupContent.add(new H4("Service Package"));
        List<Integer> list = new ArrayList<Integer>();
        list.add(12);
        list.add(24);
        list.add(36);
        ComboBox period = new ComboBox("Months purchased:");
        period.setItems(list);
        popupContent.add(singleFormatDatePicker);
    	popupContent.add(period);
    	popupContent.add(confirm);
    	confirm.addClickListener(e -> {binder.getBean().setTimePurchased((int) period.getValue());binder.getBean().setServPack(SP); binder.getBean().setStartDate(singleFormatDatePicker.getValue());});
    	return popupContent;
    	
    }
    
    private VerticalLayout optAddLayout(OptionalProduct optProd) {
    	VerticalLayout popupContent = new VerticalLayout();
    	popupContent.add(new H4("Do you wish to add: " + optProd.getName() + " to your package."));
    	popupContent.add(confirm);
    	confirm.addClickListener(e -> {binder.getBean().getOptionalProducts().add(optProd);});
    	return popupContent;
    }


	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
		
	}

}
