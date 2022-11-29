package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    //@Scheduled(fixedRate = 2000)
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

//        MovieRepository movieRepository = run.getBean(MovieRepository.class);
//        MovieService movieService = new MovieService(movieRepository);
//
//        movieService.addMovie("Batman");
//        movieService.addMovie("The Batman");
//        //movieService.addMovie("Harry Potter");
//        movieService.addMovie("Pulp Fiction");
//        movieService.addMovie("The Shawshank Redemption");

//        HttpClient client = HttpClient.newHttpClient();
//        MovieRepository movieRepository = run.getBean(MovieRepository.class);
//        List<Movie> movies;
//
//        String title = "The+Batman";
//
//        HttpRequest request = HttpRequest.newBuilder(
//                        URI.create("https://www.omdbapi.com/?t=" + title + "&apikey=d3d95a11"))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        JSONObject jsonObject = new JSONObject(response.body());
//
//        Movie movie = new Movie(
//                jsonObject.getString("Title"),
//                jsonObject.getString("Plot"),
//                jsonObject.getString("Genre"),
//                jsonObject.getString("Director"),
//                jsonObject.getString("Poster")
//        );
//
//        movieRepository.save(movie);
//
//        movie.getFavouriteMovies().add(movie);
//        movies = movie.getFavouriteMovies();
//        movieRepository.saveAll(movies);
    }
}

