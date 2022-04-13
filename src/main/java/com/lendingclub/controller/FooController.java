package com.lendingclub.controller;

import com.lendingclub.model.entity.Movie;
import com.lendingclub.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/foo")
@Validated
public class FooController {

//    @Autowired
//    private CaoService caoService;
//
//    @Autowired
//    private UserRepository userRepository;
//
    @Autowired
    private MovieRepository movieRepository;

//    @Autowired
//    private SessionFactory sessionFactory;

//    @GetMapping(value = "/hello")
//    public Iterable<Movie> helloWorld() {
//
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        User user1 = session.get(User.class, 1L);
//        User user2 = session.get(User.class, 2L);
//        User user3 = session.get(User.class, 1L); // agora ele nao vai no banco de dados.
//        session.createQuery("from User where id = 1");
//        transaction.commit();
//        session.close();

//        try {
//            caoService.addBoth();
//        } catch (IllegalStateException e) {
//             it's ok, go ahead brow
//        }
//        return caoService.retrieveAllMovies();
//    }
//
//    @PostMapping("/different")
//    public User dontKnowYet(@RequestBody User user) {
//        Set<Movie> movies = user.getMovies();
//        List<String> moviesNames = movies.stream().map(Movie::getName).collect(Collectors.toList());
//        List<Movie> persistedMovies = movieRepository.findByNameIn(moviesNames);
//        List<Movie> moviesToPersist = movies.stream()
//            .filter(movie -> !persistedMovies.stream().map(Movie::getName).collect(Collectors.toSet()).contains(movie.getName()))
//            .toList();
//        movieRepository.saveAll(moviesToPersist);
//
//        HashSet<Movie> movies1 = new HashSet<>();
//        movies1.addAll(persistedMovies);
//        movies1.addAll(moviesToPersist);
//        user.setMovies(movies1);
//
//        return userRepository.save(user);
//    }
//
//    @PostMapping("/like/{username}/{movie}")
//    public void like() {
//        throw new IllegalStateException("not implemented yet");
//    }
//
//    @DeleteMapping("/like/{username}/{movie}")
//    public void unlike() {
//        throw new IllegalStateException("not implemented yet");
//    }

}
