package com.example.data.controller;

import com.example.data.entities.FavouriteMovie;
import com.example.data.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/all")
    public List<FavouriteMovie> findAll() {
        return movieService.findAll();
    }

    @GetMapping
    public Optional<FavouriteMovie> findById(@RequestParam Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public void addFavouriteMovie(@RequestBody FavouriteMovie favouriteMovie) {
        movieService.saveFavouriteMovie(favouriteMovie);
    }

    @PatchMapping("/update/{id}")
    public FavouriteMovie update(@PathVariable Long id, @RequestBody Map<Object, Object> objectMap) {
        return movieService.updateFavouriteMovie(id, objectMap);

    }

    @DeleteMapping
    public void deleteFavouriteMovie(@RequestParam Long id) {
        movieService.deleteById(id);
    }
}
