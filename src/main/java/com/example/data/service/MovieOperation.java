package com.example.data.service;

import com.example.data.entities.FavouriteMovie;
import com.example.data.entities.Movie;
import com.example.data.repositories.FavouriteMovieRepository;
import lombok.Data;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class MovieOperation {
    private List<Movie> movies;
    private final FavouriteMovieRepository favouriteMovieRepository;

    @Autowired
    public MovieOperation(FavouriteMovieRepository favouriteMovieRepository) {
        this.favouriteMovieRepository = favouriteMovieRepository;
        this.movies = new ArrayList<>();
    }

    @SneakyThrows
    public void saveMovie(String title) {
        title = title.replaceAll(" ", "+");

        HttpResponse<String> response = getResponse("https://www.omdbapi.com/?s=" + title + "&apikey=d3d95a11");

        JSONObject jsonObject = new JSONObject(response.body());

        JSONArray jsonArray = jsonObject.getJSONArray("Search");

        for (int i = 0; i < jsonArray.length(); ++i) {
            String imdbID = jsonArray.getJSONObject(i).get("imdbID").toString();

            response = getResponse("https://www.omdbapi.com/?i=" + imdbID + "&apikey=d3d95a11");
            JSONObject movieFromJson = new JSONObject(response.body());

            Movie movie = new Movie(
                    null,
                    movieFromJson.getString("imdbID"),
                    movieFromJson.getString("Title"),
                    movieFromJson.getString("Plot"),
                    movieFromJson.getString("Genre"),
                    movieFromJson.getString("Director"),
                    movieFromJson.getString("Poster")
            );
            movies.add(movie);
        }
    }

    public static HttpResponse<String> getResponse(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(url))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void addFavouriteMovie(Movie movie) {
        FavouriteMovie favouriteMovie = new FavouriteMovie(
                null,
                movie.getImdbID(),
                movie.getTitle(),
                movie.getPlot(),
                movie.getGenre(),
                movie.getDirector(),
                movie.getPoster()
        );
        if (!favouriteMovieRepository.findAll()
                .stream()
                .map(FavouriteMovie::getImdbID)
                .anyMatch(e -> e.equals(favouriteMovie.getImdbID()))) {
            favouriteMovieRepository.save(favouriteMovie);
        }
    }

    public void clearMovieList() {
        movies.clear();
    }

    public List<FavouriteMovie> findAllFavouriteMovies(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return favouriteMovieRepository.findAll();
        } else {
            return favouriteMovieRepository.search(stringFilter);
        }
    }

}