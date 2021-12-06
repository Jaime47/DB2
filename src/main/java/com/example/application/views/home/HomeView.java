package com.example.application.views.home;

import com.example.application.data.entity.ServicePackage;
import com.example.application.data.service.ServicePackageService;
import com.example.application.views.MainLayout;
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

import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.vaadin.artur.helpers.CrudServiceDataProvider;

@PageTitle("Home")
@Route(value = "home/:servicePackageID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends Div implements BeforeEnterObserver {

    private final String SERVICEPACKAGE_ID = "servicePackageID";

    private Grid<ServicePackage> grid = new Grid<>(ServicePackage.class, false);
    private Grid<ServicePackage> rebuyGrid = new Grid<>(ServicePackage.class, false);



    private Button buy = new Button("Buy");

    private BeanValidationBinder<ServicePackage> binder;


    private ServicePackageService servicePackageService;

    public HomeView(@Autowired ServicePackageService servicePackageService) {
        this.servicePackageService = servicePackageService;
        addClassNames("home-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createRebuyLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        TemplateRenderer<ServicePackage> iconRenderer = TemplateRenderer
                .<ServicePackage>of("<img style='height: 64px' src='[[item.icon]]' />")
                .withProperty("icon", ServicePackage::getIcon);
        grid.addColumn(iconRenderer).setHeader("Icon").setWidth("68px").setFlexGrow(0);

        grid.addColumn("name").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(servicePackageService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

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
                event.forwardTo(HomeView.class);
            }
        }
    }

    private void createRebuyLayout(SplitLayout splitLayout) {
        Div rebuyWrapper = new Div();
        rebuyWrapper.setId("rebuy-wrapper");
        rebuyWrapper.setWidthFull();
        createButtonLayout(rebuyWrapper);
        splitLayout.addToSecondary(rebuyWrapper);
        rebuyWrapper.add(rebuyGrid);
    }

    private void createButtonLayout(Div div) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        
        buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(buy);
        div.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }


    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }




}
