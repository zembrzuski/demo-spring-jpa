package com.lendingclub.repository;

import com.google.common.collect.Sets;
import com.lendingclub.model.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User userA = User.builder().username("a@a.com").fullName("a").password("x").movies(Sets.newHashSet()).build();
    private User userB = User.builder().username("b@b.com").fullName("b").password("x").movies(Sets.newHashSet()).build();
    private User userC = User.builder().username("c@c.com").fullName("c").password("x").movies(Sets.newHashSet()).build();

    @BeforeAll
    public void setDatabase() {
        userRepository.saveAll(Sets.newHashSet(userA, userB, userC));
        userB.addFriend(userC);
        userRepository.save(userB);
    }


    @Test
    public void findByUsername() {
        User user = userRepository.findByUsername("a@a.com").get();
        assertEquals(userA, user);
    }

    @Test
    public void findByUsernameWithFriends() {
        User result = userRepository.findByUsernameWithFriends("b@b.com").get();
        assertEquals(result.getUsername(), "b@b.com");
        assertTrue(result.getFriends().contains(userC));
    }

    @Test
    public void findByUsernameNoFriends() {
        User result = userRepository.findByUsername("b@b.com").get();
        assertEquals(result.getUsername(), "b@b.com");
        assertTrue(result.getFriends().contains(userC));
    }

}
