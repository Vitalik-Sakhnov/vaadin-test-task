package com.haulmont.vaadintesttask.ui;

import com.haulmont.vaadintesttask.ui.doctor.DoctorView;
import com.haulmont.vaadintesttask.ui.patient.PatientView;
import com.haulmont.vaadintesttask.ui.recipe.RecipeView;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vaadin.ui.themes.ValoTheme.BUTTON_LINK;
import static com.vaadin.ui.themes.ValoTheme.MENU_ITEM;

@SpringUI
@StyleSheet({"http://localhost:8080/styles.css"})
public class MainUI extends UI {
    private PatientView patientView;
    private DoctorView doctorView;
    private RecipeView recipeView;

    @Autowired
    public MainUI(PatientView patientView, DoctorView doctorView, RecipeView recipeView) {
        this.patientView = patientView;
        this.doctorView = doctorView;
        this.recipeView = recipeView;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout rootLayout = new HorizontalLayout();
        rootLayout.setSizeFull();

        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("panel");
        panel.setId("panel");
        panel.setWidth("80%");

        HorizontalLayout mainArea = new HorizontalLayout();
        mainArea.setWidth("80%");
        Navigator navigator = new Navigator(this, panel);
        navigator.addView("", patientView);
        navigator.addView("showPatient", patientView);
        navigator.addView("showDoctor", doctorView);
        navigator.addView("showRecipe", recipeView);

        CssLayout sideNav = new CssLayout();
        sideNav.setSizeFull();
        sideNav.addStyleName("sidenav");
        sideNav.setId("sideNav");
        sideNav.setWidth("20%");

        Button patientButton = new Button("A patient", e -> navigator.navigateTo("showPatient"));
        patientButton.addStyleNames(BUTTON_LINK, MENU_ITEM);
        Button doctorButton = new Button("Doctor", e -> navigator.navigateTo("showDoctor"));
        doctorButton.addStyleNames(BUTTON_LINK, MENU_ITEM);
        Button recipeButton = new Button("Recipe", e -> navigator.navigateTo("showRecipe"));
        recipeButton.addStyleNames(BUTTON_LINK, MENU_ITEM);

        sideNav.addComponent(patientButton);
        sideNav.addComponent(doctorButton);
        sideNav.addComponent(recipeButton);

        rootLayout.addComponent(sideNav);
        rootLayout.addComponent(panel);
        setContent(rootLayout);
    }
}
