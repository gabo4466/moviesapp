package com.moviesapp.moviesappartifact.services;

import com.moviesapp.moviesappartifact.models.MoviesModel;
import com.moviesapp.moviesappartifact.repositories.IMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MoviesService {

    private final IMoviesRepository movieRepository;
    @Autowired
    public MoviesService(IMoviesRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public ArrayList<MoviesModel> getAllMovies() {
        return (ArrayList<MoviesModel>) this.movieRepository.findAll();
    }

    public MoviesModel saveMovie(MoviesModel movie) {
        return this.movieRepository.save(movie);
    }

    public Optional<MoviesModel> getMovieById(Long id) {
        return this.movieRepository.findById(id);
    }

    public MoviesModel updateById(MoviesModel request, Long id) {

        MoviesModel movie = this.movieRepository.findById(id).get();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        return this.movieRepository.save(movie);
    }

    public boolean deleteMovieById(Long id) {
        try {
            this.movieRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
