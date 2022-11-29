package com.example.views.listView;

import com.example.data.entities.Movie;
import com.example.data.service.MovieService;
import com.example.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Movies")
public class ListView extends VerticalLayout {

    Grid<Movie> grid = new Grid<>(Movie.class);
    TextField filterText = new TextField();
    MovieForm movieForm;
    MovieService movieService;

    public ListView(MovieService movieService) {
        this.movieService = movieService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, movieForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, movieForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        movieForm = new MovieForm();
        movieForm.setWidth("25em");

        movieForm.addListener(MovieForm.SaveEvent.class, this::saveContact);
        movieForm.addListener(MovieForm.CloseEvent.class, e -> closeEditor());
    }

    //TUTAJ DODAC FUNKCJE KTORA DODAJE DO ULUBIONYCH
    private void saveContact(MovieForm.SaveEvent event) {
        //FUNKCJA
        Notification.show("XDDDDDD");
        updateList();
        closeEditor();
    }

    public void configureGrid() {
        grid.addClassNames("movie-grid");
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

        grid.asSingleSelect().addValueChangeListener(event -> editMovie(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Find your movie...");
        filterText.setClearButtonVisible(true);

        Button addContactButton = new Button("Search");
        addContactButton.addClickListener(click -> addMovie());
        addContactButton.addClickShortcut(Key.ENTER);

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editMovie(Movie movie) {
        if(movie == null) {
            closeEditor();
        } else {
            movieForm.setMovie(movie);
            movieForm.poster.setSrc(movie.getPoster());
            movieForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        movieForm.setMovie(null);
        movieForm.setVisible(false);
        removeClassName("editing");
    }

    private void addMovie() {
        if(filterText.getValue() == null) {
            Notification.show("Cannot be empty");
        } else {
            movieService.saveMovie(filterText.getValue());
            updateList();
        }
    }

    public void updateList() {
        grid.setItems(movieService.findAllMovies());
    }
}
