package com.haulmont.vaadintesttask.ui.doctor;

import com.haulmont.vaadintesttask.services.impl.DoctorServiceImpl;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Doctor;
import com.haulmont.vaadintesttask.models.Specialization;

public class EditWindow extends Window {
    private final Logger logger = LogManager.getLogger();

    public EditWindow(Grid<Doctor> grid, DoctorServiceImpl doctorService, Doctor doctor) {
        super("Editing doctor");
        center();
        setModal(true);
        VerticalLayout rootLayout = new VerticalLayout();
        Binder<Doctor> doctorBinder = new Binder<>();
        ComboBox<Specialization> specializationComboBox = new ComboBox<>("Specialization");
        specializationComboBox.setItems(Specialization.values());

        TextField surnameField = new TextField("Surname");
        TextField nameField = new TextField("Name");
        TextField patronymicField = new TextField("Patronymic");

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

        doctorBinder.readBean(doctor);

        Button editButton = new Button("Ok",
                clickEvent -> {
                    if (doctorBinder.isValid()) {
                        try {
                            doctorBinder.writeBean(doctor);
                            doctorService.update(doctor.getId(), doctor);
                            logger.info("Edited doctor: \"" + doctor + "\"");
                            grid.setItems(doctorService.findAll());
                            grid.getDataProvider().refreshAll();
                            close();
                        } catch (ValidationException e) {
                            Notification.show("Some fields are filled incorrectly");
                            logger.error("Doctor edit error: \"" + doctor + "\"", e);
                        }
                    } else {
                        Notification.show("Some fields are filled incorrectly");
                        logger.error("Doctor edit error: \"" + doctor + "\"");
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
        specializationComboBox.setWidth("328px");

        rootLayout.addComponent(surnameField);
        rootLayout.addComponent(nameField);
        rootLayout.addComponent(patronymicField);
        rootLayout.addComponent(specializationComboBox);
        rootLayout.addComponent(buttons);
        rootLayout.setWidth("352px");
        setContent(rootLayout);
    }
}
