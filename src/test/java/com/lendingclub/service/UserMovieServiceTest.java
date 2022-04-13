package com.lendingclub.service;

import com.google.common.collect.Sets;
import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.MovieRepository;
import com.lendingclub.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { MovieHelper.class, MovieRepository.class, UserRepository.class, UserMovieService.class })
@WebMvcTest
class UserMovieServiceTest {

    @MockBean
    private MovieHelper movieHelper;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserMovieService userMovieService;

    @Test
    public void saveUserWithPersistedAndNonPersistedMovie() {
        Movie pulpFiction = Movie.builder().name("pulp fiction").build();
        Movie django = Movie.builder().name("django").build();
        Set<Movie> movies = Sets.newHashSet(pulpFiction, django);
        List<String> moviesNames = Lists.newArrayList("pulp fiction", "django");
        List<Movie> moviesToPersist = Lists.newArrayList(django);
        List<Movie> persistedMovies = Lists.newArrayList(pulpFiction);
        User user = User.builder().username("jo@globo.com").movies(movies).build();

        Predicate<Movie> moviePredicate = Mockito.mock(Predicate.class);
        Mockito.when(movieHelper.persistedMoviePredicate(persistedMovies)).thenReturn(moviePredicate);
        Mockito.when(moviePredicate.test(pulpFiction)).thenReturn(true);
        Mockito.when(moviePredicate.test(django)).thenReturn(false);
        Mockito.when(moviePredicate.negate()).thenReturn((t) -> !moviePredicate.test(t));

        Mockito.when(movieHelper.getMoviesNames(movies)).thenReturn(moviesNames);
        Mockito.when(movieRepository.findByNameInOrderByName(moviesNames)).thenReturn(persistedMovies);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User user1 = userMovieService.persistUserAndMovies(user);

        Mockito.verify(movieRepository).saveAll(moviesToPersist);
        Mockito.verify(userRepository).save(user);
        assertEquals(user1.getMovies(), movies);
    }

}
