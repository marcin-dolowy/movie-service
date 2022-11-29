package com.example.data.service;

import com.example.data.entities.FavouriteMovie;
import com.example.data.entities.Movie;
import com.example.data.repositories.FavouriteMovieRepository;
import com.example.data.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final FavouriteMovieRepository favouriteMovieRepository;

    @SneakyThrows
    public void saveMovie(String title) {
        title = title.replaceAll(" ", "+");

        HttpResponse<String> response = getResponse("https://www.omdbapi.com/?s=" + title + "&apikey=d3d95a11");

        JSONObject jsonObject = new JSONObject(response.body());

        JSONArray jsonArray = jsonObject.getJSONArray("Search");

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            String imdbID = jsonObject1.get("imdbID").toString();

            response = getResponse("https://www.omdbapi.com/?i=" + imdbID + "&apikey=d3d95a11");
            JSONObject jsonObject2 = new JSONObject(response.body());

            Movie movie = new Movie(
                    null,
                    jsonObject2.getString("Title"),
                    jsonObject2.getString("Plot"),
                    jsonObject2.getString("Genre"),
                    jsonObject2.getString("Director"),
                    jsonObject2.getString("Poster")
            );
            movies.add(movie);
        }
        movieRepository.saveAll(movies);
    }

    public static HttpResponse<String> getResponse(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(url))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void addFavouriteMovie(Long id) {
        FavouriteMovie favouriteMovie = new FavouriteMovie();
        favouriteMovie.setMovie_id(id);
        favouriteMovieRepository.save(favouriteMovie);
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }
}
