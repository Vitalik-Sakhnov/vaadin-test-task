package com.haulmont.vaadintesttask.ui.recipe;

import com.haulmont.vaadintesttask.services.impl.PatientServiceImpl;
import com.haulmont.vaadintesttask.services.impl.RecipeServiceImpl;
import com.haulmont.vaadintesttask.services.impl.DoctorServiceImpl;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.haulmont.vaadintesttask.models.Recipe;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RecipeView extends Composite implements View {
    private RecipeServiceImpl recipeService;
    private PatientServiceImpl patientService;
    private DoctorServiceImpl doctorService;
    private Recipe selectedRecipe;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public RecipeView(RecipeServiceImpl recipeService, PatientServiceImpl patientService, DoctorServiceImpl doctorService) {
        this.recipeService = recipeService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @PostConstruct
    public void init() {
        Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        ListDataProvider<Recipe> dataProvider = new ListDataProvider<>(recipeService.findAll());

        Button addButton = new Button("Add",
                clickEvent -> getUI().addWindow(
                        new AddWindow(dataProvider, recipeService, doctorService, patientService)
                )
        );

        Button editButton = new Button("Edit",
                clickEvent -> {
                    if (selectedRecipe != null) {
                        getUI().addWindow(new EditWindow(
                                recipeGrid, recipeService, doctorService, patientService, selectedRecipe)
                        );
                    } else {
                        Notification.show("Recipe not selected");
                    }
                });

        Button deleteButton = new Button("Delete",
                clickEvent -> {
                    if (selectedRecipe != null) {
                        recipeService.delete(selectedRecipe);
                        logger.info("Remote recipe: \"" + selectedRecipe + "\"");
                        recipeGrid.setItems(recipeService.findAll());
                        recipeGrid.getDataProvider().refreshAll();
                    } else {
                        Notification.show("Recipe not selected");
                    }
                }
        );

        buttons.addComponent(addButton);
        buttons.addComponent(editButton);
        buttons.addComponent(deleteButton);

        TextField descriptionFilter = new TextField();
        TextField patientFilter = new TextField();
        TextField priorityFilter = new TextField();

        recipeGrid.setDataProvider(dataProvider);
        recipeGrid.setSizeFull();
        recipeGrid.setColumnOrder("id", "description", "patient", "doctor", "creationDate", "expireDate",
                "validity", "priority");
        recipeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        recipeGrid.getColumn("description").setWidth(250);
        recipeGrid.getColumn("priority").setWidth(150);

        HeaderRow filters = recipeGrid.appendHeaderRow();

        patientFilter.addValueChangeListener(
                event -> dataProvider.addFilter(
                        recipe -> StringUtils.containsIgnoreCase(
                                recipe.getPatient().toString(), patientFilter.getValue()
                        )
                )
        );
        descriptionFilter.addValueChangeListener(
                event -> dataProvider.addFilter(
                        recipe -> StringUtils.containsIgnoreCase(
                                recipe.getDescription(),
                                descriptionFilter.getValue()
                        )
                )
        );
        priorityFilter.addValueChangeListener(
                event -> dataProvider.addFilter(
                        recipe -> StringUtils.containsIgnoreCase(
                                String.valueOf(recipe.getPriority()),
                                priorityFilter.getValue()
                        )
                )
        );

        recipeGrid.addItemClickListener(itemClick -> selectedRecipe = itemClick.getItem());

        filters.getCell("description").setComponent(descriptionFilter);
        filters.getCell("patient").setComponent(patientFilter);
        filters.getCell("priority").setComponent(priorityFilter);

        descriptionFilter.setSizeFull();
        patientFilter.setSizeFull();
        priorityFilter.setSizeFull();

        recipeGrid.setWidth("1200px");
        rootLayout.addComponent(recipeGrid);
        rootLayout.addComponent(buttons);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }
}