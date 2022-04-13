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

//
//    @Test
//    public void testMyBeautifulGraph() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/a@a.com/b@b.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/a@a.com/d@d.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/b@b.com/a@a.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/b@b.com/d@d.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/b@b.com/e@e.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/c@c.com/d@d.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/c@c.com/h@h.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/d@d.com/c@c.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/e@e.com/b@b.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/e@e.com/h@h.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/e@e.com/i@i.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/f@f.com/g@g.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/g@g.com/f@f.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/g@g.com/h@h.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/g@g.com/i@i.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/h@h.com/c@c.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/h@h.com/e@e.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/h@h.com/g@g.com")).andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/i@i.com/e@e.com")).andExpect(status().isNoContent());
//        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/i@i.com/g@g.com")).andExpect(status().isNoContent());
//
//        Set<Friend> aFriends = userService.findFriendsGraph("a@a.com", 1, 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("b@b.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("d@d.com")).findFirst().get().getLevel(), 1);
//        assertEquals(2, aFriends.size());
//
//        aFriends = userService.findFriendsGraph("a@a.com", 1, 2);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("b@b.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("d@d.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("c@c.com")).findFirst().get().getLevel(), 2);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("e@e.com")).findFirst().get().getLevel(), 2);
//        assertEquals(4, aFriends.size());
//
//        aFriends = userService.findFriendsGraph("a@a.com", 1, 3);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("b@b.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("d@d.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("c@c.com")).findFirst().get().getLevel(), 2);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("e@e.com")).findFirst().get().getLevel(), 2);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("h@h.com")).findFirst().get().getLevel(), 3);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("i@i.com")).findFirst().get().getLevel(), 3);
//        assertEquals(6, aFriends.size());
//
//        aFriends = userService.findFriendsGraph("i@i.com", 1, 3);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("e@e.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("g@g.com")).findFirst().get().getLevel(), 1);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("b@b.com")).findFirst().get().getLevel(), 2);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("f@f.com")).findFirst().get().getLevel(), 2);
//        assertEquals(aFriends.stream().filter(x -> x.getUsername().equals("h@h.com")).findFirst().get().getLevel(), 2);
//    }

}
