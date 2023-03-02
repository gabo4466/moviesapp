package com.moviesapp.moviesappartifact.controllers;

import com.moviesapp.moviesappartifact.models.MoviesModel;
import com.moviesapp.moviesappartifact.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        String response;
        boolean deleted = this.moviesService.deleteMovieById(id);
        if (deleted) {
            response = "Movie deleted";
        }else{
            response = "Movie not deleted";
        }
        return response;
    }

    @PostMapping(path = "/image/{id}")
    public String uploadImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        String response;
        if (file.isEmpty()) {
            response = "File is empty";
        }else {
            response = this.moviesService.uploadImage(file, id);
        }

        return response;

    }

    @GetMapping(path = "/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") Long id) {
        byte[] image = this.moviesService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }


}
