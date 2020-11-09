package com.haulmont.vaadintesttask.ui.recipe;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.*;
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

public class AddWindow extends Window {
    private final Logger logger = LogManager.getLogger();
    private Recipe recipe;

    public AddWindow(ListDataProvider<Recipe> dataProvider, RecipeServiceImpl recipeService, DoctorServiceImpl doctorService, PatientServiceImpl patientService) {
        super("Adding a recipe");
        center();
        setModal(true);
        VerticalLayout rootLayout = new VerticalLayout();

        TextArea descriptionField = new TextArea("Description");
        ComboBox<Patient> patientComboBox = new ComboBox<>("Patient");
        patientComboBox.setItems(patientService.findAll());
        patientComboBox.setItemCaptionGenerator(patient -> patient.getId() + " " + patient.getSurname() + " " +
                patient.getName() + " " + patient.getPatronymic());

        ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doctor");
        doctorComboBox.setItems(doctorService.findAll());
        doctorComboBox.setItemCaptionGenerator(doctor -> doctor.getId() + " " + doctor.getSpecialization() + " " +
                doctor.getSurname() + " " + doctor.getName());

        DateField creationDate = new DateField("Creation Date");
        TextField validityField = new TextField("Expires in months");
        ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");
        priorityComboBox.setItems(Priority.values());

        Binder<Recipe> recipeBinder = new Binder<>(Recipe.class);
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
                new LongRangeValidator("Invalid validity", 1L, 12L)
        ).bind(Recipe::getValidity, Recipe::setValidity);

        recipeBinder.forField(priorityComboBox).withValidator(
                new BeanValidator(Recipe.class, "priority")
        ).bind(Recipe::getPriority, Recipe::setPriority);

        Button add = new Button("Ok",
                clickEvent -> {
                    if (recipeBinder.isValid()) {
                        recipe = new Recipe(descriptionField.getValue(), patientComboBox.getValue(),
                                doctorComboBox.getValue(), creationDate.getValue(),
                                Long.parseLong(validityField.getValue()), priorityComboBox.getValue()
                        );
                        recipeService.save(recipe);
                        dataProvider.getItems().add(recipe);
                        logger.info("Added Recipe: \"" + recipe + "\"");
                        dataProvider.refreshAll();
                        descriptionField.clear();
                        patientComboBox.clear();
                        doctorComboBox.clear();
                        creationDate.clear();
                        close();
                    } else {
                        Notification.show("Some fields are filled incorrectly");
                        logger.error("Recipe add error");
                    }
                }
        );
        Button cancel = new Button("Cancel", event -> close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(add);
        buttons.addComponent(cancel);

        descriptionField.setWidth("378px");
        patientComboBox.setWidth("378px");
        doctorComboBox.setWidth("378px");
        creationDate.setWidth("378px");
        validityField.setWidth("378px");
        priorityComboBox.setWidth("378px");
        rootLayout.setWidth("402px");

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
