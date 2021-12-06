package com.example.application.views.confirmation;

import com.example.application.data.entity.ServicePackage;
import com.example.application.data.service.ServicePackageService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.vaadin.artur.helpers.CrudServiceDataProvider;

@PageTitle("Buy Service")
@Route(value = "buyservice", layout = MainLayout.class)
public class BuyServiceView extends Div implements BeforeEnterObserver {

    private final String SERVICEPACKAGE_ID = "servicePackageID";

    private Grid<ServicePackage> packageGrid = new Grid<>(ServicePackage.class, false);
    private Grid<ServicePackage> optionalProdGrid = new Grid<>(ServicePackage.class, false);

    private Dialog dialogConfirm = new Dialog();
    private Button buy = new Button("Buy", e -> dialogConfirm.open());
    private Button add = new Button("Add");

    private BeanValidationBinder<ServicePackage> binder;


    private ServicePackageService servicePackageService;

    public BuyServiceView(@Autowired ServicePackageService servicePackageService) {
        this.servicePackageService = servicePackageService;
        addClassNames("buyservice-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createPackageGridLayout(splitLayout);
        createOptProdLayout(splitLayout);
        add(splitLayout);
        
        dialogConfirm.add(confirmationLayout());


        // Configure Grid
        TemplateRenderer<ServicePackage> iconRenderer = TemplateRenderer
                .<ServicePackage>of("<img style='height: 64px' src='[[item.icon]]' />")
                .withProperty("icon", ServicePackage::getIcon);
        packageGrid.addColumn(iconRenderer).setHeader("Icon").setWidth("68px").setFlexGrow(0);

        packageGrid.addColumn("name").setAutoWidth(true);
        packageGrid.setDataProvider(new CrudServiceDataProvider<>(servicePackageService));
        packageGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        packageGrid.setHeightFull();

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> servicePackageId = event.getRouteParameters().getInteger(SERVICEPACKAGE_ID);
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
                event.forwardTo(BuyServiceView.class);
            }
        }
    }
    
    private void createPackageGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        wrapper.add(new H3("Service Packages"));
        createPackageButtonLayout(wrapper);
        splitLayout.addToPrimary(wrapper);
        wrapper.add(packageGrid);
    }
    
    private void createOptProdLayout(SplitLayout splitLayout) {
        Div optProdWrapper = new Div();
        optProdWrapper.setId("rebuy-wrapper");
        optProdWrapper.setWidthFull();
        optProdWrapper.add(new H3("Optional Products"));
        createOptProdButtonLayout(optProdWrapper);
        splitLayout.addToSecondary(optProdWrapper);
        optProdWrapper.add(optionalProdGrid);
    }

    private void createPackageButtonLayout(Div div) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(buy);
        div.add(buttonLayout);
    }
    
    private void createOptProdButtonLayout(Div div) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(add);
        div.add(buttonLayout);
    }


    private void refreshGrid() {
        packageGrid.select(null);
        packageGrid.getDataProvider().refreshAll();
    }
    
    
    // Pop up CONFIRMATION
    
    private VerticalLayout confirmationLayout() {
    	VerticalLayout popupContent = new VerticalLayout();
    	popupContent.add(new H4("Service Package"));
    	popupContent.add(new Text("Movistar plus"));
    	popupContent.add(new Button("Confirm"));
    	return popupContent;
    	
    }




}
