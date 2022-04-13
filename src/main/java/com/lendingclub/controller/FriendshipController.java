package com.lendingclub.controller;

import com.lendingclub.model.entity.User;
import com.lendingclub.service.command.LinkUnlinkCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/friendship")
public class FriendshipController {

    private final LinkUnlinkCommand linkUnlinkCommand;

    public FriendshipController(@Qualifier(value = "friendshipCommand") LinkUnlinkCommand linkUnlinkCommand) {
        this.linkUnlinkCommand = linkUnlinkCommand;
    }

    @PostMapping(value = "/{user1}/{user2}")
    public User addFriend(@PathVariable("user1") String username1, @PathVariable("user2") String username2) {
        return linkUnlinkCommand.link(username1, username2);
    }

    @DeleteMapping(value = "/{user1}/{user2}")
    public User removeFriend(@PathVariable("user1") String username1, @PathVariable("user2") String username2) {
        return linkUnlinkCommand.unlink(username1, username2);
    }

}
