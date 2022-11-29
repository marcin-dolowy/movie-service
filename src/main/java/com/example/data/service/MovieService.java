package com.example.data.service;

import com.example.data.entities.Movie;
import com.example.data.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @SneakyThrows
    public void saveMovie(String title) {
        title = title.replaceAll(" ", "+");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("https://www.omdbapi.com/?t=" + title + "&apikey=d3d95a11"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        Movie movie = new Movie(
                null,
                jsonObject.getString("Title"),
                jsonObject.getString("Plot"),
                jsonObject.getString("Genre"),
                jsonObject.getString("Director"),
                jsonObject.getString("Poster")
        );

        movieRepository.save(movie);
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }
}
