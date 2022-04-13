package com.lendingclub.service.command;

import com.google.common.collect.Sets;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @InjectMocks
    private FriendshipService friendshipService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void makeFriendship_success() {
        User userA = User.builder().username("a").build();
        User userB = User.builder().username("b").build();
        when(userRepository.findByUsername("a")).thenReturn(Optional.of(userA));
        when(userRepository.findByUsername("b")).thenReturn(Optional.of(userB));

        User result = friendshipService.makeFriendship("a", "b");

        assertTrue(result.getFriends().contains(userB));

        verify(userRepository, times(1)).save(userB);
    }

    @Test
    public void makeFriendship_error() {
        when(userRepository.findByUsername("a")).thenThrow(new IllegalStateException("some error"));

        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {
            friendshipService.makeFriendship("a", "b");
        }, "some error");
    }

    @Test
    public void unmakeFriendship() {
        User userC = User.builder().username("c").friends(Sets.newHashSet()).build();
        User userB = User.builder().username("b").friends(Sets.newHashSet()).build();
        User userA = User.builder().username("a").friends(Sets.newHashSet(userB, userC)).build();
        when(userRepository.findByUsername("a")).thenReturn(Optional.of(userA));
        when(userRepository.save(userA)).thenReturn(userA);

        User result = friendshipService.unmakeFriendship("a", "b");

        assertTrue(result.getFriends().contains(userC));
        assertFalse(result.getFriends().contains(userB));
        verify(userRepository, times(1)).save(userA);
    }

}
