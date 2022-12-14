package com.example.data.service;

import com.example.data.entities.FavouriteMovie;
import com.example.data.entities.Movie;
import com.example.data.repository.FavouriteMovieRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Data
@Service
@RequiredArgsConstructor
public class MovieService {

    private List<Movie> movies = new ArrayList<>();
    private final FavouriteMovieRepository favouriteMovieRepository;

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

            Movie movie = getMovie(movieFromJson);
            movies.add(movie);
        }
    }

    private static Movie getMovie(JSONObject movieFromJson) {
        return new Movie(
                movieFromJson.getString("imdbID"),
                movieFromJson.getString("Title"),
                movieFromJson.getString("Plot"),
                movieFromJson.getString("Genre"),
                movieFromJson.getString("Director"),
                movieFromJson.getString("Poster")
        );
    }

    public static HttpResponse<String> getResponse(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(url))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public boolean addFavouriteMovie(Movie movie) {
        FavouriteMovie favouriteMovie = new FavouriteMovie(
                null,
                movie.getImdbID(),
                movie.getTitle(),
                movie.getPlot(),
                movie.getGenre(),
                movie.getDirector(),
                movie.getPoster()
        );
        if (favouriteMovieRepository.findAll()
                .stream()
                .map(FavouriteMovie::getImdbID)
                .noneMatch(e -> e.equals(favouriteMovie.getImdbID()))) {
            favouriteMovieRepository.save(favouriteMovie);
            return true;
        }
        return false;
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

    public List<FavouriteMovie> findAll() {
        return favouriteMovieRepository.findAll();
    }

    public Optional<FavouriteMovie> findById(Long id) {
        return favouriteMovieRepository.findById(id);
    }

    public void deleteById(Long id) {
        favouriteMovieRepository.deleteById(id);
    }

    public void saveFavouriteMovie(FavouriteMovie favouriteMovie) {
        favouriteMovieRepository.save(favouriteMovie);
    }

    public FavouriteMovie updateFavouriteMovie(Long id, Map<Object, Object> objectMap) {
        FavouriteMovie favouriteMovie = favouriteMovieRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        objectMap.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(FavouriteMovie.class, (String) key);
            Objects.requireNonNull(field).setAccessible(true);
            ReflectionUtils.setField(field, favouriteMovie, value);
        });
        return favouriteMovieRepository.save(favouriteMovie);
    }
}
