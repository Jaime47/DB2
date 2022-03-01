package com.example.application.views.employee;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.application.data.Order;
import com.example.application.data.Service.FixedInternetService;
import com.example.application.data.Service.MyService;
import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.Service.ServiceType;
import com.example.application.data.entity.User;
import com.example.application.data.repository.OptionalProductRepository;
import com.example.application.data.repository.OrderRepository;
import com.example.application.data.repository.ServicePackageRepository;
import com.example.application.data.repository.ServiceRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.views.BuyServiceView;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;

@PageTitle("eHome")
@Route(value = "e-home")
public class eHomeView extends Div implements BeforeEnterObserver {


    public eHomeView(HttpServletRequest request, OptionalProductRepository OPR, ServiceRepository SR) {
    
        addClassNames("home-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createServicePackageLayout(splitLayout, OPR, SR);
        createOptionalProductLayout(splitLayout);
        add(splitLayout);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }

    private void createOptionalProductLayout(SplitLayout splitLayout) {
    	TextField optProdName = new TextField("Insert name: ");
    	TextField optProdPrice = new TextField("Insert price: ");
        Div wrapper = new Div();
        wrapper.setId("OP-wrapper");
        wrapper.setWidthFull();
        wrapper.add(optProdName);
        wrapper.add(optProdPrice);
        createRoutingLayout(wrapper);
        splitLayout.addToSecondary(wrapper);
        

    }

    private void createRoutingLayout(Div div) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button create = new Button("Create");
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        
        buttonLayout.add(create);
        div.add(buttonLayout);
    }

    private void createServicePackageLayout(SplitLayout splitLayout, OptionalProductRepository OPR, ServiceRepository SR) {
    	TextField spName = new TextField("Insert name: ");
    	NumberField spPrice12 = new NumberField("Insert price (12 months): ");
    	NumberField spPrice24 = new NumberField("Insert price (24 months): ");
    	NumberField spPrice36 = new NumberField("Insert price (36 months): ");
        Div wrapper = new Div();
        wrapper.setId("SP-wrapper");
        wrapper.add(spName,spPrice12,spPrice24,spPrice36);
        wrapper.setWidthFull();
        createRoutingLayout(wrapper);
        SplitLayout gridLayout = createGridSecondarySplitLayout(OPR, SR);
        wrapper.add(gridLayout);
        splitLayout.addToPrimary(wrapper);
    }

    
    private SplitLayout createGridSecondarySplitLayout(OptionalProductRepository OPR, ServiceRepository SR) {
    	
        Grid<FixedInternetService> Sgrid = new Grid<>(FixedInternetService.class,false);
        Grid<OptionalProduct> OPgrid = new Grid<>(OptionalProduct.class,false);
		Sgrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		Sgrid.setHeightFull();
		OPgrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		OPgrid.setHeightFull();
		
		Sgrid.setItems((FixedInternetService)SR.findByServiceType(ServiceType.INTERNETFIXED));
		OPgrid.setItems(OPR.findAll());
		
		Sgrid.addColumns("id");
		Sgrid.addColumn(serv -> {Checkbox checkbox = new Checkbox();
		checkbox.setValue(false);
		return checkbox;}).setHeader("Selected");
		
		
		OPgrid.addColumns("name","price");
		OPgrid.addColumn(serv -> {Checkbox checkbox = new Checkbox();
		checkbox.setValue(false);
		return checkbox;}).setHeader("Selected");
		
    	SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(Sgrid);
        splitLayout.addToSecondary(OPgrid);
        
        return splitLayout;
  
    }

    
    private void refreshGrid(Grid grid) {
        grid.getDataProvider().refreshAll();
    }



}
