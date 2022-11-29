package com.example.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String plot;
    private String genre;
    private String director;
    private String poster;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "favourite_movies"
//    )
//    private List<Movie> favouriteMovies = new ArrayList<>();

//    public Movie(String title, String plot, String genre, String director, String poster) {
//        this.title = title;
//        this.plot = plot;
//        this.genre = genre;
//        this.director = director;
//        this.poster = poster;
//    }
}
