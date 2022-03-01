package com.example.application.views;


import com.example.application.data.Order;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.entity.User;
import com.example.application.data.repository.OrderRepository;
import com.example.application.data.repository.ServicePackageRepository;
import com.example.application.data.repository.UserRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.NumberRenderer;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends Div implements BeforeEnterObserver {

    private final String SERVICEPACKAGE_ID = "servicePackageID";

    private Grid<ServicePackage> grid = new Grid<>(ServicePackage.class, false);
    private Grid<Order> rebuyGrid = new Grid<>(Order.class, false);

    private Button rebuy = new Button("Rebuy");

    private BeanValidationBinder<ServicePackage> binder;


 

    public HomeView(HttpServletRequest request, ServicePackageRepository SPR, OrderRepository OR, UserRepository UR) {
    	
        addClassNames("home-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createRebuyLayout(splitLayout);

        add(splitLayout);


        // Configure Grid
        //Example of config located in buy service view
		grid.addColumn("packageName").setAutoWidth(true);	
		grid.addColumns("twelveMonths","twentyFourMonths","thirtySixMonths");
		grid.setItems(SPR.findAll());
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setHeightFull();
		
		//Instance of rebuy grid, only if user is logged In
        if(request.getSession().getAttribute("User") == null) {
        	splitLayout.getSecondaryComponent().setVisible(false);
        }else {
        	UserDetails userDetails = (UserDetails) request.getSession().getAttribute("User");
        	Optional<User> currentUser = UR.findByUserName(userDetails.getUsername());
        	currentUser.ifPresent(usr ->rebuyGrid.setItems(OR.findByAcceptedAndUser_id(false, usr.getId())));
        	rebuyGrid.addColumn(("id")).setHeader("Order Id");
        	rebuyGrid.addColumn(("date")).setHeader("Purchase date");
        	rebuyGrid.addColumn("servPack.packageName").setHeader("Service Package");
        	rebuyGrid.addItemDoubleClickListener(e -> request.getSession().setAttribute("Order", e.getItem()));
        	rebuyGrid.addItemDoubleClickListener(e -> rebuyGrid.getUI().ifPresent(u -> u.navigate("checkout-form")));
        }

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        /*Optional<Integer> servicePackageId = event.getRouteParameters().getInteger(SERVICEPACKAGE_ID);
        if (servicePackageId.isPresent()) {
            Optional<ServicePackage> servicePackageFromBackend = servicePackageService.get(servicePackageId.get());
            if (servicePackageFromBackend.isPresent()) {
            } else {
                Notification.show(
                        String.format("The requested servicePackage was not found, ID = %d", servicePackageId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(HomeView.class);
            }
        }*/
    }

    private void createRebuyLayout(SplitLayout splitLayout) {
        Div rebuyWrapper = new Div();
        rebuyWrapper.setId("rebuy-wrapper");
        rebuyWrapper.setWidthFull();
        
        splitLayout.addToSecondary(rebuyWrapper);
        rebuyWrapper.add(rebuyGrid);
    }

    private void createRoutingLayout(Div div) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        RouterLink routerToBuy = new RouterLink("Buy", BuyServiceView.class);
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        
        buttonLayout.add(routerToBuy);
        div.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        createRoutingLayout(wrapper);
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }


    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }




}
