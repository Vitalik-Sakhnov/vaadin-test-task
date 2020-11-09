package com.haulmont.vaadintesttask.ui.doctor;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;

import com.haulmont.vaadintesttask.models.Doctor;
import com.haulmont.vaadintesttask.models.Specialization;
import com.haulmont.vaadintesttask.services.impl.DoctorServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddWindow extends Window {
    private final Logger logger = LogManager.getLogger();
    private Doctor doctor;

    public AddWindow(ListDataProvider<Doctor> dataProvider, DoctorServiceImpl doctorService) {
        super("Adding a doctor");
        center();
        setModal(true);
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();

        TextField surnameField = new TextField("Surname");
        TextField nameField = new TextField("Name");
        TextField patronymicField = new TextField("Patronymic");
        ComboBox<Specialization> specializationComboBox = new ComboBox<>("Specialization");
        specializationComboBox.setItems(Specialization.values());

        Binder<Doctor> doctorBinder = new Binder<>(Doctor.class);
        doctorBinder.forField(surnameField).withValidator(
                new BeanValidator(Doctor.class, "surname")
        ).bind(Doctor::getSurname, Doctor::setSurname);

        doctorBinder.forField(nameField).withValidator(
                new BeanValidator(Doctor.class, "name")
        ).bind(Doctor::getName, Doctor::setName);

        doctorBinder.forField(patronymicField).withValidator(
                new BeanValidator(Doctor.class, "patronymic")
        ).bind(Doctor::getPatronymic, Doctor::setPatronymic);

        doctorBinder.forField(specializationComboBox).withValidator(
                new BeanValidator(Doctor.class, "specialization")
        ).bind(Doctor::getSpecialization, Doctor::setSpecialization);

        Button addButton = new Button("Ok",
                clickEvent -> {
                    if (doctorBinder.isValid()) {
                        doctor = new Doctor(
                                nameField.getValue(), surnameField.getValue(), patronymicField.getValue(),
                                specializationComboBox.getValue()
                        );
                        doctorService.save(doctor);
                        dataProvider.getItems().add(doctor);
                        logger.info("Added Doctor: \"" + doctor + "\"");
                        dataProvider.refreshAll();
                        surnameField.clear();
                        nameField.clear();
                        patronymicField.clear();
                        specializationComboBox.clear();
                        close();
                    } else {
                        Notification.show("Some fields are filled incorrectly");
                        logger.error("Doctor add error");
                    }
                }
        );
        Button cancelButton = new Button("Cancel", event -> close());

        buttons.addComponent(addButton);
        buttons.addComponent(cancelButton);

        surnameField.setWidth("328px");
        nameField.setWidth("328px");
        patronymicField.setWidth("328px");
        specializationComboBox.setWidth("328px");
        rootLayout.setWidth("352px");

        // Add components into a rootLayout
        rootLayout.addComponent(surnameField);
        rootLayout.addComponent(nameField);
        rootLayout.addComponent(patronymicField);
        rootLayout.addComponent(specializationComboBox);
        rootLayout.addComponent(buttons);
        setContent(rootLayout);
    }
}