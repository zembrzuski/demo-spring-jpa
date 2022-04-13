package com.lendingclub.service;

import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class MovieHelper {

    public Predicate<Movie> persistedMoviePredicate(List<Movie> persistedMovies) {
        return movie -> persistedMovies.stream()
            .map(Movie::getName)
            .collect(Collectors.toSet())
            .contains(movie.getName());
    }

    public List<String> getMoviesNames(Set<Movie> movies) {
        return movies.stream()
            .map(Movie::getName)
            .collect(Collectors.toList());
    }

}
