package com.example.data.entities;

import lombok.*;

import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass

public class Movie {

    private String imdbID;
    private String title;
    private String plot;
    private String genre;
    private String director;
    private String poster;

}
