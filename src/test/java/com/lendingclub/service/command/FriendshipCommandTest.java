package com.lendingclub.service.command;

import com.lendingclub.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipCommandTest {

    @InjectMocks
    private FriendshipCommand friendshipCommand;

    @Mock
    private FriendshipService friendshipService;

    private User userA = new User();

    @Test
    public void link() {
        when(friendshipService.makeFriendship("a", "b")).thenReturn(userA);
        assertEquals(userA, friendshipCommand.link("a", "b"));
    }

    @Test
    public void unlink() {
        when(friendshipService.unmakeFriendship("a", "b")).thenReturn(userA);
        assertEquals(userA, friendshipCommand.unlink("a", "b"));
    }

}
