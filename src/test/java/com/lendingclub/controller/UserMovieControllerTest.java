package com.lendingclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.lendingclub.model.entity.Address;
import com.lendingclub.model.entity.Genre;
import com.lendingclub.model.entity.Movie;
import com.lendingclub.model.entity.User;
import com.lendingclub.service.UserMovieService;
import com.lendingclub.service.command.LinkUnlinkCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserMovieController.class)
class UserMovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserMovieService userMovieService;

    @Qualifier(value = "movieCommand")
    @MockBean
    private LinkUnlinkCommand linkUnlinkCommand;

    @Test
    public void postComplexForm_validationIssues_badRequest() throws Exception {
        User user = User.builder().build();

        mockMvc.perform(
            post("/complex-form")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("password").value("must not be empty"))
            .andExpect(jsonPath("fullName").value("must not be empty"));
    }

    @Test
    public void postComplexForm_validUser_success() throws Exception {
        Movie movie = Movie.builder().name("moviename").genre(Genre.DRAMA).build();
        Address address = Address.builder().zipcode("96180-000").build();

        User user = User.builder()
            .username("jo@globo.com")
            .password("password")
            .fullName("satriani")
            .movies(Collections.singleton(movie))
            .address(address)
            .build();

        when(userMovieService.persistUserAndMovies(user)).thenReturn(user);

        mockMvc.perform(
                post("/complex-form")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("jo@globo.com"))
            .andExpect(jsonPath("fullName").value("satriani"))
            .andExpect(jsonPath("movies[0].name").value("moviename"))
            .andExpect(jsonPath("movies[0].genre").value("DRAMA"));
    }

    @Test
    public void likeMovie_validMovie_success() throws Exception {
        User user = User.builder()
            .username("a@a.com")
            .fullName("aaa")
            .movies(Sets.newHashSet(Movie.builder().name("django").genre(Genre.DRAMA).build()))
            .build();

        when(linkUnlinkCommand.link("a@a.com", "django")).thenReturn(user);

        mockMvc.perform(
                post("/complex-form/like/a@a.com/django")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("a@a.com"))
            .andExpect(jsonPath("fullName").value("aaa"))
            .andExpect(jsonPath("movies[0].name").value("django"))
            .andExpect(jsonPath("movies[0].genre").value("DRAMA"));
    }

    @Test
    public void likeMovie_invalidMovie_beautifulErrorMessage() throws Exception {
        when(linkUnlinkCommand.link("a@a.com", "django"))
            .thenThrow(new IllegalArgumentException("not found"));

        mockMvc.perform(
                post("/complex-form/like/a@a.com/django")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").value("not found"));
    }

    @Test
    public void dislikeMovie_validArguments_success() {
        // TODO implement it.
    }

    @Test
    public void dislikeMovie_invalidArguments_beautifulMessage() {
        // TODO implement it.
    }

}
