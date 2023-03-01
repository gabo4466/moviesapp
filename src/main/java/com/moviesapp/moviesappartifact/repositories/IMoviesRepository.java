package com.moviesapp.moviesappartifact.repositories;

import com.moviesapp.moviesappartifact.models.MoviesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMoviesRepository extends JpaRepository<MoviesModel, Long> {

}
