package com.haulmont.vaadintesttask.ui.recipe;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.LongRangeValidator;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Doctor;
import com.haulmont.vaadintesttask.models.Patient;
import com.haulmont.vaadintesttask.models.Priority;
import com.haulmont.vaadintesttask.models.Recipe;
import com.haulmont.vaadintesttask.services.impl.DoctorServiceImpl;
import com.haulmont.vaadintesttask.services.impl.PatientServiceImpl;
import com.haulmont.vaadintesttask.services.impl.RecipeServiceImpl;

import java.time.LocalDate;

public class EditWindow extends Window {
    private final Logger logger = LogManager.getLogger();

    public EditWindow(Grid<Recipe> grid, RecipeServiceImpl recipeService, DoctorServiceImpl doctorService,
                      PatientServiceImpl patientService, Recipe recipe) {
        super("Editing recipe");
        center();
        setModal(true);
        VerticalLayout rootLayout = new VerticalLayout();
        Binder<Recipe> recipeBinder = new Binder<>(Recipe.class);

        TextArea descriptionField = new TextArea("Description");
        descriptionField.setSizeFull();

        ComboBox<Patient> patientComboBox = new ComboBox<>("Patient");
        patientComboBox.setItems(patientService.findAll());
        patientComboBox.setSizeFull();

        ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doctor");
        doctorComboBox.setItems(doctorService.findAll());
        doctorComboBox.setSizeFull();

        DateField creationDate = new DateField("Creation Date");
        creationDate.setSizeFull();

        TextField validityField = new TextField("Expires in months");
        validityField.setSizeFull();

        ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");
        priorityComboBox.setItems(Priority.values());
        priorityComboBox.setSizeFull();

        recipeBinder.forField(descriptionField).withValidator(
                new BeanValidator(Recipe.class, "description")
        ).bind(Recipe::getDescription, Recipe::setDescription);

        recipeBinder.forField(patientComboBox).withValidator(
                new BeanValidator(Recipe.class, "patient")
        ).bind(Recipe::getPatient, Recipe::setPatient);

        recipeBinder.forField(doctorComboBox).withValidator(
                new BeanValidator(Recipe.class, "doctor")
        ).bind(Recipe::getDoctor, Recipe::setDoctor);

        recipeBinder.forField(creationDate).withValidator(
                new DateRangeValidator("Invalid Date", LocalDate.now().minusYears(1), LocalDate.now())
        ).bind(Recipe::getCreationDate, Recipe::setCreationDate);

        recipeBinder.forField(validityField).withConverter(
                new StringToLongConverter("Not a number")).withValidator(
                new LongRangeValidator("Validity is out of range", 1L, 12L)
        ).bind(Recipe::getValidity, Recipe::setValidity);

        recipeBinder.forField(priorityComboBox).withValidator(
                new BeanValidator(Recipe.class, "priority")
        ).bind(Recipe::getPriority, Recipe::setPriority);

        recipeBinder.readBean(recipe);

        Button edit = new Button("Ok",
                clickEvent -> {
                    if (recipeBinder.isValid()) {
                        try {
                            recipeBinder.writeBean(recipe);
                            recipeService.update(recipe.getId(), recipe);
                            logger.info("Edited recipe: \"" + recipe + "\"");
                            grid.setItems(recipeService.findAll());
                            grid.getDataProvider().refreshAll();
                            close();
                        } catch (ValidationException e) {
                            Notification.show("Some fields are filled incorrectly");
                            logger.error("Recipe edit error: \"" + recipe + "\"", e);
                        }
                    } else {
                        Notification.show("Some fields are filled incorrectly");
                        logger.error("Recipe edit error: \"" + recipe + "\"");
                    }
                }
        );
        Button cancel = new Button("Cancel", event -> close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(edit);
        buttons.addComponent(cancel);

        descriptionField.setWidth("428px");
        patientComboBox.setWidth("428px");
        doctorComboBox.setWidth("428px");
        creationDate.setWidth("428px");
        validityField.setWidth("428px");
        priorityComboBox.setWidth("428px");
        rootLayout.setWidth("452px");

        // Add components into a rootLayout
        rootLayout.addComponent(descriptionField);
        rootLayout.addComponent(patientComboBox);
        rootLayout.addComponent(doctorComboBox);
        rootLayout.addComponent(creationDate);
        rootLayout.addComponent(validityField);
        rootLayout.addComponent(priorityComboBox);
        rootLayout.addComponent(buttons);
        setContent(rootLayout);
    }
}
