package com.lendingclub.service;

import com.lendingclub.model.entity.Genre;
import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.MovieRepository;
import com.lendingclub.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CaoService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public CaoService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void addBoth() {
        User rodrigo = new User("rodrigo");
        Movie pulpFiction = new Movie("pulp fiction", Genre.FANTASY);

        rodrigo.addMovie(pulpFiction);

        movieRepository.save(pulpFiction);

//        if (1 > 0) {
//            throw new IllegalStateException("aee");
//        }

        userRepository.save(rodrigo);
    }

    public Iterable<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public Iterable<Movie> retrieveAllMovies() {
        return movieRepository.findAll();
    }

}
