package com.lendingclub.service;

import com.google.common.collect.Sets;
import com.lendingclub.dto.Friend;
import com.lendingclub.model.entity.User;
import com.lendingclub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<Friend> findFriendsGraph(String username, int currentLevel, int maxLevel) {
        Set<Friend> friendship = Sets.newHashSet();

        User user = userRepository.findByUsernameWithFriends(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("could not find on database %s", username)));

        user.getFriends()
            .forEach(friend -> friendship.add(new Friend(friend.getUsername(), currentLevel)));

        if (currentLevel < maxLevel) {
            user.getFriends()
                .forEach(friend -> {
                    Set<Friend> friendsGraph = findFriendsGraph(friend.getUsername(), currentLevel + 1, maxLevel);

                    friendsGraph.removeIf(x -> x.getUsername().equals(username));

                    friendship.addAll(friendsGraph);
                });
        }

        return friendship;
    }


}
