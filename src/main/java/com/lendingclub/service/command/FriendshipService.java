package com.lendingclub.service.command;

import com.lendingclub.model.entity.User;
import com.lendingclub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

    private final UserRepository userRepository;

    public FriendshipService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User makeFriendship(String username, String friend) {
        User user1 = getUser(username);
        User user2 = getUser(friend);

        user1.addFriend(user2);
        userRepository.save(user2);

        return user1;
    }

    public User unmakeFriendship(String username, String friend) {
        User user = getUser(username);
        user.getFriends().removeIf(u -> friend.equals(u.getUsername()));

        return userRepository.save(user);
    }

    private User getUser(String username) {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find user: %s", username)));
    }

}
