package com.haulmont.vaadintesttask.ui.patient;

import com.haulmont.vaadintesttask.services.impl.PatientServiceImpl;
import com.haulmont.vaadintesttask.services.impl.RecipeServiceImpl;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Patient;
import com.haulmont.vaadintesttask.models.Recipe;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PatientView extends Composite implements View {
    private PatientServiceImpl patientService;
    private RecipeServiceImpl recipeService;
    private Patient selectedPatient;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public PatientView(PatientServiceImpl patientService, RecipeServiceImpl recipeService) {
        this.patientService = patientService;
        this.recipeService = recipeService;
    }

    @PostConstruct
    public void init() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Grid<Patient> patientGrid = new Grid<>(Patient.class);
        ListDataProvider<Patient> dataProvider = new ListDataProvider<>(patientService.findAll());

        Button addButton = new Button("Add",
                clickEvent -> getUI().addWindow(new AddWindow(dataProvider, patientGrid, patientService))
        );

        Button editButton = new Button("Edit",
                clickEvent -> {
                    if (selectedPatient != null) {
                        getUI().addWindow(new EditWindow(patientGrid, patientService, selectedPatient));
                    } else {
                        Notification.show("Patient not selected");
                    }
                }
        );

        Button deleteButton = new Button("Delete",
                clickEvent -> {
                    if (selectedPatient != null) {
                        patientService.delete(selectedPatient);
                        logger.info("Remote patient: \"" + selectedPatient + "\"");
                        patientGrid.setItems(patientService.findAll());
                        patientGrid.getDataProvider().refreshAll();
                    } else {
                        Notification.show("Patient not selected");
                    }
                }
        );

        buttons.addComponent(addButton);
        buttons.addComponent(editButton);
        buttons.addComponent(deleteButton);

        patientGrid.setDataProvider(dataProvider);
        patientGrid.setSizeFull();
        patientGrid.removeColumn("recipes");
        patientGrid.setColumnOrder("id", "surname", "name", "patronymic", "phone");
        patientGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        patientGrid.addItemClickListener(itemClick -> {
            deleteButton.setEnabled(true);
            selectedPatient = itemClick.getItem();
            if (selectedPatient != null) {
                for (Recipe recipe : recipeService.findAll()) {
                    if (recipe.getPatient().getId().equals(selectedPatient.getId())) {
                        deleteButton.setEnabled(false);
                        break;
                    }
                }
            }
        });

        patientGrid.setWidth("800px");
        rootLayout.addComponent(patientGrid);
        rootLayout.addComponent(buttons);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }
}
