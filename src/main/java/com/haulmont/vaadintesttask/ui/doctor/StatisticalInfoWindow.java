package com.haulmont.vaadintesttask.ui.doctor;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.haulmont.vaadintesttask.models.Doctor;
import com.haulmont.vaadintesttask.services.impl.DoctorServiceImpl;

public class StatisticalInfoWindow extends Window {
    public StatisticalInfoWindow(DoctorServiceImpl doctorService) {
        super("Doctor statistical information");
        center();
        setModal(true);
        Grid<Doctor> doctorGrid = new Grid<>();
        ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(doctorService.findAll());
        VerticalLayout rootLayout = new VerticalLayout();

        doctorGrid.setDataProvider(dataProvider);
        doctorGrid.setSizeFull();
        doctorGrid.addColumn(
                doctor -> doctor.getId() + ": " + doctor.getSpecialization() + " "
                        + doctor.getSurname() + " " + doctor.getName()
        ).setWidth(426).setCaption("Doctor");
        doctorGrid.addColumn(
                doctor -> doctor.getRecipes().size()
        ).setWidth(150).setCaption("Recipes number");

        rootLayout.addComponent(doctorGrid);
        rootLayout.setWidth("600px");
        setContent(rootLayout);
    }
}
