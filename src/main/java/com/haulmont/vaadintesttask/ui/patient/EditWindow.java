package com.haulmont.vaadintesttask.ui.patient;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Patient;
import com.haulmont.vaadintesttask.services.impl.PatientServiceImpl;

public class EditWindow extends Window {
    private final Logger logger = LogManager.getLogger();

    public EditWindow(Grid<Patient> grid, PatientServiceImpl patientService, Patient patient) {
        super("Editing patient");
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

        patientBinder.readBean(patient);

        Button editButton = new Button("Ok",
                clickEvent -> {
                    if (patientBinder.isValid()) {
                        try {
                            patientBinder.writeBean(patient);
                            patientService.update(patient.getId(), patient);
                            logger.info("Edited patient: \"" + patient + "\"");
                            grid.setItems(patientService.findAll());
                            grid.getDataProvider().refreshAll();
                            close();
                        } catch (ValidationException e) {
                            Notification.show("Some fields are filled incorrectly");
                            logger.error("Patient edit error: \"" + patient + "\"", e);
                        }
                    } else {
                        Notification.show("Some fields are filled incorrectly");
                        logger.error("Patient edit error: \"" + patient + "\"");
                    }
                }
        );
        Button cancelButton = new Button("Cancel", event -> close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(editButton);
        buttons.addComponent(cancelButton);

        surnameField.setWidth("328px");
        nameField.setWidth("328px");
        patronymicField.setWidth("328px");
        phoneField.setWidth("328px");
        rootLayout.setWidth("352px");

        // Add components into a rootLayout
        rootLayout.addComponent(nameField);
        rootLayout.addComponent(surnameField);
        rootLayout.addComponent(patronymicField);
        rootLayout.addComponent(phoneField);
        rootLayout.addComponent(buttons);
        setContent(rootLayout);
    }
}
