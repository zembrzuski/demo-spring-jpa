package com.lendingclub.service.command;

import com.lendingclub.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendshipCommand implements LinkUnlinkCommand {

    @Autowired
    private FriendshipService friendshipService;

    @Override
    public User link(String username, String friend) {
        return friendshipService.makeFriendship(username, friend);
    }

    @Override
    public User unlink(String username, String friend) {
        return friendshipService.unmakeFriendship(username, friend);
    }
}
