package com.example;

import com.example.data.repositories.MovieRepository;
import com.example.data.service.MovieService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class DemoApplication {

    //@Scheduled(fixedRate = 2000)
    public static void main(String[] args) throws IOException, InterruptedException {

        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

        MovieRepository movieRepository = run.getBean(MovieRepository.class);
        MovieService movieService = new MovieService(movieRepository);

        String title = "Harry+Potter";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("https://www.omdbapi.com/?s=" + title + "&apikey=d3d95a11"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //JSONObject jsonObject = new JSONObject(response.body());

        //JSONArray jsonArray = new JSONArray(response.body());
        String strJSON = response.body();
        System.out.println(strJSON);

        JSONArray jsonArray = (JSONArray) new JSONTokener(strJSON).nextValue();
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        System.out.println(jsonObject.toString());


        System.out.println("===============");





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

