package com.example.views.favouritemovieview;

import com.example.data.entities.Movie;
import com.example.views.MainLayout;
import com.example.views.listView.ListView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "favouriteMovies", layout = MainLayout.class)
@PageTitle("Favourite Movies")
public class FavouriteMovieView extends VerticalLayout {

    Grid<Movie> grid = new Grid<>(Movie.class);
    TextField filterText = new TextField();

    public FavouriteMovieView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
    }

    private void configureGrid() {
        grid.addClassNames("favourite-movie-grid");
        grid.setSizeFull();
        grid.setColumns("title", "genre", "director");

        grid.addComponentColumn(i -> {
            TextArea textArea = new TextArea();
            textArea.setValue(i.getPlot());
            textArea.setWidthFull();
            textArea.setMinWidth(500, Unit.PIXELS);
            textArea.setReadOnly(true);
            return textArea;
        }).setHeader("Plot");

        grid.addComponentColumn(i -> {
            Image image = new Image(i.getPoster(), "alt text");
            image.setWidth(90, Unit.PIXELS);
            image.setHeight(134, Unit.PIXELS);
            return image;
        }).setHeader("Poster");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);


        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
