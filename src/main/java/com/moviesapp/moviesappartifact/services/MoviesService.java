package com.moviesapp.moviesappartifact.services;

import com.moviesapp.moviesappartifact.models.MoviesModel;
import com.moviesapp.moviesappartifact.repositories.IMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MoviesService {



    private static String IMAGES_DIRECTORY;

    private final IMoviesRepository movieRepository;
    @Autowired
    public MoviesService(IMoviesRepository movieRepository, Environment env) {
        this.movieRepository = movieRepository;
        IMAGES_DIRECTORY = env.getProperty("file.upload-dir");

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

    public String uploadImage(MultipartFile file, Long id) {
        try{
            MoviesModel movie = this.movieRepository.findById(id).get();
            String[] fileSplit = file.getOriginalFilename().split("\\.");
            String extension = "."+fileSplit[fileSplit.length - 1];
            String fileName = movie.getId() + "_" + movie.getTitle() + "_" + extension;
            file.transferTo(new File(IMAGES_DIRECTORY + fileName));
            movie.setImage(fileName);
            this.movieRepository.save(movie);

            return "Image uploaded succesfully: " + fileName;
        } catch (Exception e) {
            return "Image not uploaded";
        }
    }

    public byte[] getImage(Long id) {
        try {
            MoviesModel movie = this.movieRepository.findById(id).get();
            String fileName = movie.getImage();
            System.out.println(IMAGES_DIRECTORY + fileName);
            File file = new File(IMAGES_DIRECTORY + fileName);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            System.out.println("exception:"+e.getMessage());
            return null;
        }
    }


}
