package com.lendingclub.service.command;

import com.lendingclub.model.entity.User;

public interface LinkUnlinkCommand {

    User link(String username, String entityToLink);

    User unlink(String username, String entityToUnLink);

}
