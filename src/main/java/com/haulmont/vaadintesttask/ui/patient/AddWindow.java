package com.haulmont.vaadintesttask.ui.patient;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Patient;
import com.haulmont.vaadintesttask.services.impl.PatientServiceImpl;

public class AddWindow extends Window {
    private final Logger logger = LogManager.getLogger();
    private Patient patient;

    public AddWindow(ListDataProvider<Patient> dataProvider, Grid<Patient> grid, PatientServiceImpl patientService) {
        super("Adding a patient");
        center();
        setModal(true);
        VerticalLayout rootLayout = new VerticalLayout();
        TextField surnameField = new TextField("Surname");
        TextField nameField = new TextField("Name");
        TextField patronymicField = new TextField("Patronymic");
        TextField phoneField = new TextField("Phone");

        Binder<Patient> patientBinder = new Binder<>();
        patientBinder.forField(nameField).withValidator(
                new BeanValidator(Patient.class, "name")
        ).bind(Patient::getName, Patient::setName);

        patientBinder.forField(surnameField).withValidator(
                new BeanValidator(Patient.class, "surname")
        ).bind(Patient::getSurname, Patient::setSurname);

        patientBinder.forField(patronymicField).withValidator(
                new BeanValidator(Patient.class, "patronymic")
        ).bind(Patient::getPatronymic, Patient::setPatronymic);

        patientBinder.forField(phoneField).withValidator(
                new BeanValidator(Patient.class, "phone")
        ).bind(Patient::getPhone, Patient::setPhone);

        Button addButton = new Button("Ok",
                clickEvent -> {
                    if (patientBinder.isValid()) {
                        patient = new Patient(nameField.getValue(), surnameField.getValue(),
                                patronymicField.getValue(), phoneField.getValue()
                        );
                        patientService.save(patient);
                        logger.info("Added Patient: \"" + patient + "\"");
                        dataProvider.getItems().add(patient);
                        grid.getDataProvider().refreshAll();
                        dataProvider.refreshAll();
                        nameField.clear();
                        surnameField.clear();
                        patronymicField.clear();
                        phoneField.clear();
                        close();
                    } else {
                        Notification.show("Some fields are filled incorrectly");
                        logger.error("Patient add error");
                    }
                });
        Button cancelButton = new Button("Cancel", event -> close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(addButton);
        buttons.addComponent(cancelButton);

        surnameField.setWidth("328px");
        nameField.setWidth("328px");
        patronymicField.setWidth("328px");
        phoneField.setWidth("328px");
        rootLayout.setWidth("352px");

        // Add components into a rootLayout
        rootLayout.addComponent(surnameField);
        rootLayout.addComponent(nameField);
        rootLayout.addComponent(patronymicField);
        rootLayout.addComponent(phoneField);
        rootLayout.addComponent(buttons);
        setContent(rootLayout);
    }
}
