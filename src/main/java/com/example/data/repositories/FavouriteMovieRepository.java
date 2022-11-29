package com.example.data.repositories;

import com.example.data.entities.FavouriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteMovieRepository extends JpaRepository<FavouriteMovie, Long> {
}
