package com.example.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

    private Long id;

    private String imdbID;
    private String title;
    private String plot;
    private String genre;
    private String director;
    private String poster;

}
