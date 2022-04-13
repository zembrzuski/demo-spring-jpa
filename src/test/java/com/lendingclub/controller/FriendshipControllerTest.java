package com.lendingclub.controller;

import com.lendingclub.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;

import com.lendingclub.service.command.LinkUnlinkCommand;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { FriendshipController.class, LinkUnlinkCommand.class })
@WebMvcTest
class FriendshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Qualifier(value = "friendshipCommand")
    @MockBean
    private LinkUnlinkCommand linkUnlinkCommand;

    @Test
    public void addFriend_validUsers_happyPath() throws Exception {
        User user = User.builder().username("a@b.com").fullName("a b").build();
        when(linkUnlinkCommand.link("user1", "user2")).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/friendship/user1/user2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("a@b.com"))
            .andExpect(jsonPath("fullName").value("a b"))
            .andReturn();
    }

    @Test
    public void addFriend_notFoundUser_error() {
        // TODO implement it.
    }

    @Test
    public void removeFriend_validUsers_happyPath() throws Exception {
        User user = User.builder().username("a@b.com").fullName("a b").build();
        when(linkUnlinkCommand.unlink("user1", "user2")).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders
                    .delete("/friendship/user1/user2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("username").value("a@b.com"))
            .andExpect(jsonPath("fullName").value("a b"))
            .andReturn();
    }

    @Test
    public void removeFriend_notFoundUser_error() {
        // TODO implement it.
    }

}
