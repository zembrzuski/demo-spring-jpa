package com.lendingclub.service.command;

import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.MovieRepository;
import com.lendingclub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public MovieService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public User likeMovie(String username, String movieName) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid username %s", username)));

        Movie movie = movieRepository.findByName(movieName)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid movie %s", movieName)));

        user.addMovie(movie);

        return userRepository.save(user);
    }

    public User unlikeMovie(String username, String movieName) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid username %s", username)));

        user.getMovies().removeIf(movie -> movieName.equals(movieName));

        return userRepository.save(user);

    }

}
