package com.lendingclub.controller;

import com.lendingclub.dto.Friend;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.UserRepository;
import com.lendingclub.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class FriendshipControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void before() {
        userRepository.deleteAll();
        userRepository.save(User.builder().username("a@a.com").fullName("a").password("a").build());
        userRepository.save(User.builder().username("b@b.com").fullName("b").password("b").build());
    }

    @Test
    public void addFriendship_removeFriendship() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/a@a.com/b@b.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("a@a.com"))
            .andExpect(jsonPath("fullName").value("a"))
            .andReturn();

        boolean result = userRepository.findByUsernameWithFriends("a@a.com")
            .get()
            .getFriends()
            .stream()
            .anyMatch(friend -> friend.getUsername().equals("b@b.com"));

        assertTrue(result);

        mockMvc.perform(MockMvcRequestBuilders.delete("/friendship/a@a.com/b@b.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("a@a.com"))
            .andExpect(jsonPath("fullName").value("a"))
            .andReturn();

        boolean result2 = userRepository.findByUsernameWithFriends("a@a.com")
            .get()
            .getFriends()
            .stream()
            .noneMatch(friend -> friend.getUsername().equals("b@b.com"));

        assertTrue(result2);
    }

}
