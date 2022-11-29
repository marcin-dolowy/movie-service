package com.example.data.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
public class FavouriteMovie extends Movie{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public FavouriteMovie(Long id, String imdbID, String title, String plot, String genre, String director, String poster) {
        super(imdbID, title, plot, genre, director, poster);
        this.id = id;
    }
}


