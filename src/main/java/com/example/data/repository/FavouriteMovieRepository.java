package com.example.data.repository;

import com.example.data.entities.FavouriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteMovieRepository extends JpaRepository<FavouriteMovie, Long> {

    @Query("select c from FavouriteMovie c " +
            "where lower(c.title) like lower(concat('%', :searchTerm, '%'))")
    List<FavouriteMovie> search(@Param("searchTerm") String searchTerm);

}
