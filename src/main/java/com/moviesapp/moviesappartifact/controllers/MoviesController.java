package com.moviesapp.moviesappartifact.controllers;

import com.moviesapp.moviesappartifact.models.MoviesModel;
import com.moviesapp.moviesappartifact.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public ArrayList<MoviesModel> getMovies() {
        return moviesService.getAllMovies();
    }

    @PostMapping
    public MoviesModel postMovies(@RequestBody MoviesModel movie) {
        return this.moviesService.saveMovie(movie);
    }

    @GetMapping(path = "/{id}")
    public Optional<MoviesModel> getMovieById(@PathVariable("id") Long id) {
        return this.moviesService.getMovieById(id);
    }

    @PatchMapping(path = "/{id}")
    public MoviesModel updateMovieById(@RequestBody MoviesModel request, @PathVariable("id") Long id) {
        return this.moviesService.updateById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteMovieById(@PathVariable("id") Long id) {
        boolean deleted = this.moviesService.deleteMovieById(id);
        if (deleted) {
            return "Movie deleted";
        }
        return "Movie not deleted";
    }


}
