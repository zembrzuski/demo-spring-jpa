package com.lendingclub.service;

import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.MovieRepository;
import com.lendingclub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    private final MovieHelper movieHelper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public UserMovieService(MovieHelper movieHelper, MovieRepository movieRepository, UserRepository userRepository) {
        this.movieHelper = movieHelper;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public User persistUserAndMovies(User user) {
        List<Movie> persistedMovies = movieRepository.findByNameInOrderByName(movieHelper.getMoviesNames(user.getMovies()));

        List<Movie> moviesToPersist = user.getMovies().stream()
                .filter(movieHelper.persistedMoviePredicate(persistedMovies).negate())
                .toList();

        movieRepository.saveAll(moviesToPersist);

        user.setMovies(getMovieEntities(persistedMovies, moviesToPersist));

        return userRepository.save(user);
    }

    private Set<Movie> getMovieEntities(List<Movie> persistedMovies, List<Movie> moviesToPersist) {
        HashSet<Movie> movies1 = new HashSet<>();

        movies1.addAll(persistedMovies);
        movies1.addAll(moviesToPersist);

        return movies1;
    }

}
