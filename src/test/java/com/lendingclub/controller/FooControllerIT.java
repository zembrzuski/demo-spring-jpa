package com.lendingclub.controller;

import com.lendingclub.repository.MovieRepository;
import com.lendingclub.repository.UserRepository;
import com.lendingclub.service.CaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {FooController.class, CaoService.class})
@WebMvcTest
class FooControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MovieRepository movieRepository;

//    @Test
//    public void testFooEndpoint() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                    .get("/foo/test")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//            )
//            .andExpect(status().isOk())
//            .andExpect(content().string("ok"))
//            .andReturn();
//    }

}
