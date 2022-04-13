package com.lendingclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.lendingclub.model.entity.Genre;
import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.MovieRepository;
import com.lendingclub.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class UserMovieControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void clean() {
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void persistUserWithNoMovies() throws Exception {
        User user = User.builder()
            .username("jo@globo.com")
            .fullName("jo soares")
            .password("123")
            .movies(Sets.newHashSet())
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/complex-form")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("jo@globo.com"))
            .andExpect(jsonPath("fullName").value("jo soares"))
            .andExpect(jsonPath("password").value("123"))
            .andExpect(jsonPath("movies").isEmpty())
            .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders
                    .get("/user/search/findByUsername?username=jo@globo.com")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("jo@globo.com"))
            .andExpect(jsonPath("fullName").value("jo soares"))
            .andExpect(jsonPath("password").value("123"))
            .andExpect(jsonPath("movies").doesNotHaveJsonPath())
            .andReturn();
    }

    @Test
    public void persistUserWithTwoMovies() throws Exception {
        Movie pulpFiction = Movie.builder().name("pulp fiction").genre(Genre.DRAMA).build();
        Movie django = Movie.builder().name("django").genre(Genre.FANTASY).build();

        User user = User.builder()
            .username("jo@globo.com")
            .fullName("jo soares")
            .password("123")
            .movies(Sets.newHashSet(pulpFiction, django))
            .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/complex-form")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders
                    .get("/user/search/findByUsername?username=jo@globo.com")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("jo@globo.com"))
            .andExpect(jsonPath("fullName").value("jo soares"))
            .andExpect(jsonPath("password").value("123"))
            .andExpect(jsonPath("movies").doesNotHaveJsonPath())
            .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders
                    .get("/movie/search/findByNameInOrderByName?movieNames=pulp fiction,django")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("_embedded.movies[0].name").value("django"))
            .andExpect(jsonPath("_embedded.movies[0].genre").value("FANTASY"))
            .andExpect(jsonPath("_embedded.movies[1].name").value("pulp fiction"))
            .andExpect(jsonPath("_embedded.movies[1].genre").value("DRAMA"))
            .andReturn();
    }

}
