package com.example.application.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route("ass")
@PermitAll
public class GridDndReorderRows extends Composite<HorizontalLayout> {
    private HorizontalLayout layout = new HorizontalLayout();

    private Person draggedItem;
    private List<Person> gridItems;

    public GridDndReorderRows() {
        gridItems = getTestData();
        Grid<Person> grid = new Grid<>(Person.class);
        grid.setColumns("firstName");
        grid.setItems(gridItems);
        grid.setSortableColumns();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setRowsDraggable(true);
        grid.setWidth("30%");

        grid.addDragStartListener(
                event -> {
                    // store current dragged item so we know what to drop
                    draggedItem = event.getDraggedItems().get(0);
                    grid.setDropMode(GridDropMode.ON_TOP);
                }
        );

        grid.addDragEndListener(
                event -> {
                    draggedItem = null;
                    // Once dragging has ended, disable drop mode so that
                    // it won't look like other dragged items can be dropped  
                    grid.setDropMode(null);
                }
        );

        grid.addDropListener(
                event -> {
                    Person dropOverItem = event.getDropTargetItem().get();
                    if (!dropOverItem.equals(draggedItem)) {
                        Collections.swap(gridItems, gridItems.indexOf(draggedItem), gridItems.indexOf(dropOverItem));

                        grid.getDataProvider().refreshAll();

                    }
                }
        );
        Grid<Person> grid2 = new Grid<>(Person.class);
        grid.setColumns("lastName");
        grid2.setItems(gridItems);
        grid2.setSortableColumns();
        grid2.setSelectionMode(Grid.SelectionMode.NONE);
        grid2.setWidth("30%");
        layout.setWidthFull();
        getContent().add(layout);
        layout.add(grid);
        layout.add(grid2);


    }

    private List<Person> getTestData() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Jake", "Peralta"));
        persons.add(new Person("Rosa", "Diaz"));
        persons.add(new Person("Terry", "Jeffords"));
        persons.add(new Person("Amy", "Santiago"));
        persons.add(new Person("Charles", "Boyle"));
        persons.add(new Person("Raymond", "Holt"));
        return persons;
    }

    public static class Person {
        private String firstName;
        private String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}