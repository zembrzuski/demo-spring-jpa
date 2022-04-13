package com.lendingclub.repository;

import com.google.common.collect.Sets;
import com.lendingclub.model.entity.Genre;
import com.lendingclub.model.entity.Movie;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @BeforeAll
    public void setDatabase() {
        movieRepository.saveAll(Sets.newHashSet(
            Movie.builder().name("django").genre(Genre.FANTASY).build(),
            Movie.builder().name("pulp fiction").genre(Genre.FANTASY).build(),
            Movie.builder().name("brooklin 99").genre(Genre.COMEDY).build()
        ));
    }

    @Test
    public void findByGenre() {
        List<Movie> expected =
            Lists.newArrayList(movieRepository.findByName("django").get(), movieRepository.findByName("pulp fiction").get());

        List<Movie> byGenre = movieRepository.findByGenre(Genre.FANTASY);

        assertThat(byGenre).hasSameElementsAs(expected);
    }

    @Test
    public void findByNameInOrderByName() {
        List<Movie> expected =
            Lists.newArrayList(movieRepository.findByName("django").get(), movieRepository.findByName("pulp fiction").get());

        List<Movie> result = movieRepository.findByNameInOrderByName(Sets.newHashSet("django", "pulp fiction"));

        assertThat(result).hasSameElementsAs(expected);
    }

}
