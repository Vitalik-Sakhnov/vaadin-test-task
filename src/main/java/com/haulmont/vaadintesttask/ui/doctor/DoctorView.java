package com.haulmont.vaadintesttask.ui.doctor;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Doctor;
import com.haulmont.vaadintesttask.models.Recipe;
import com.haulmont.vaadintesttask.services.impl.DoctorServiceImpl;
import com.haulmont.vaadintesttask.services.impl.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DoctorView extends Composite implements View {
    private DoctorServiceImpl doctorService;
    private RecipeServiceImpl recipeService;
    private Doctor selectedDoctor;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public DoctorView(DoctorServiceImpl doctorService, RecipeServiceImpl recipeService) {
        this.doctorService = doctorService;
        this.recipeService = recipeService;
    }

    @PostConstruct
    public void init() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(doctorService.findAll());
        Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);

        Button addButton = new Button("Add",
                clickEvent -> getUI().addWindow(new AddWindow(dataProvider, doctorService))
        );

        Button editButton = new Button("Edit",
                clickEvent -> {
                    Doctor doctor = selectedDoctor;
                    if (doctor != null) {
                        getUI().addWindow(new EditWindow(doctorGrid, doctorService, doctor));
                    } else {
                        Notification.show("Doctor not selected");
                    }
                }
        );

        Button deleteButton = new Button("Delete",
                clickEvent -> {
                    if (selectedDoctor != null) {
                        doctorService.delete(selectedDoctor);
                        logger.info("Remote doctor: \"" + selectedDoctor + "\"");
                        doctorGrid.setItems(doctorService.findAll());
                        doctorGrid.getDataProvider().refreshAll();
                    } else {
                        Notification.show("Doctor not selected");
                    }
                }
        );

        Button statisticalInfoButton = new Button("Statistical information",
                clickEvent -> getUI().addWindow(new StatisticalInfoWindow(doctorService))
        );

        doctorGrid.setDataProvider(dataProvider);
        doctorGrid.setSizeFull();
        doctorGrid.removeColumn("recipes");
        doctorGrid.setColumnOrder("id", "surname", "name", "patronymic", "specialization");
        doctorGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        doctorGrid.addItemClickListener(itemClick -> {
            deleteButton.setEnabled(true);
            selectedDoctor = itemClick.getItem();
            if (selectedDoctor != null) {
                for (Recipe recipe : recipeService.findAll()) {
                    if (recipe.getDoctor().getId().equals(selectedDoctor.getId())) {
                        deleteButton.setEnabled(false);
                        break;
                    }
                }
            }
        });

        buttons.addComponent(addButton);
        buttons.addComponent(editButton);
        buttons.addComponent(deleteButton);
        buttons.addComponent(statisticalInfoButton);

        doctorGrid.setWidth("800px");
        rootLayout.addComponent(doctorGrid);
        rootLayout.addComponent(buttons);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }
}