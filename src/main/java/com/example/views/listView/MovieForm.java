package com.example.views.listView;

import com.example.data.entities.Movie;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class MovieForm extends FormLayout {

    TextField title = new TextField("Title");
    TextField plot = new TextField("Plot");
    TextField genre = new TextField("Genre");
    TextField director = new TextField("Director");
    Image poster = new Image();

    Button save = new Button("Add to favourite");
    Button close = new Button("Cancel");

    Binder<Movie> binder = new BeanValidationBinder<>(Movie.class);
    private Movie movie;

    public MovieForm() {
        addClassName("movie-form");
        binder.bindInstanceFields(this);

        setTextFieldForReadOnly();

        add(title, plot, genre, director, poster, createButtonLayouts());
    }

    private void setTextFieldForReadOnly() {
        title.setReadOnly(true);
        plot.setReadOnly(true);
        genre.setReadOnly(true);
        director.setReadOnly(true);
    }

    private HorizontalLayout createButtonLayouts() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> addToFavourite());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, close);
    }

    private void addToFavourite() {
        try {
            binder.writeBean(movie);
            fireEvent(new SaveEvent(this, movie));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        binder.readBean(movie);
    }

    public static abstract class MovieFormEvent extends ComponentEvent<MovieForm> {
        private final Movie movie;

        protected MovieFormEvent(MovieForm source, Movie movie) {
            super(source, false);
            this.movie = movie;
        }

        public Movie getMovie() {
            return movie;
        }
    }

    public static class SaveEvent extends MovieFormEvent {
        SaveEvent(MovieForm source, Movie movie) {
            super(source, movie);
        }
    }

    public static class CloseEvent extends MovieFormEvent {
        CloseEvent(MovieForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
