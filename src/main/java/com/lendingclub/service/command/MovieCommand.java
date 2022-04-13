package com.lendingclub.service.command;

import com.lendingclub.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieCommand implements LinkUnlinkCommand {

    @Autowired
    private MovieService movieService;

    @Override
    public User link(String username, String movie) {
        return movieService.likeMovie(username, movie);
    }

    @Override
    public User unlink(String username, String movie) {
        return movieService.unlikeMovie(username, movie);
    }

}
