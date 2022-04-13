package com.lendingclub.service;

import com.google.common.collect.Sets;
import com.lendingclub.model.entity.Movie;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieHelperTest {

    private static final Movie m1 = Movie.builder().name("pulp fiction").build();
    private static final Movie m2 = Movie.builder().name("jango unchained").build();

    private MovieHelper movieHelper = new MovieHelper();

    @Test
    public void shoudReturnTrueForPersistedMovie() {
        List<Movie> persistedMovies = Arrays.asList(m1, m2);

        assertTrue(movieHelper.persistedMoviePredicate(persistedMovies).test(m2));
    }

    @Test
    public void shoudReturnFalseForNonPersistedMovie() {
        List<Movie> persistedMovies = Arrays.asList(m1);

        assertFalse(movieHelper.persistedMoviePredicate(persistedMovies).test(m2));
    }

    @Test
    public void getNames() {
        List<String> moviesNames = movieHelper.getMoviesNames(Sets.newHashSet(m1, m2));
        List<String> expected = Lists.newArrayList("pulp fiction", "jango unchained");

        assertEquals(moviesNames, expected);
    }
}
