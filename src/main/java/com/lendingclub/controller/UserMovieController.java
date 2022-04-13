package com.lendingclub.controller;

import com.lendingclub.model.entity.User;
import com.lendingclub.service.UserMovieService;
import com.lendingclub.service.command.LinkUnlinkCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/complex-form")
public class UserMovieController {

    private final UserMovieService userMovieService;
    private final LinkUnlinkCommand linkUnlinkCommand;

    public UserMovieController(UserMovieService userMovieService, @Qualifier(value = "movieCommand") LinkUnlinkCommand linkUnlinkCommand) {
        this.userMovieService = userMovieService;
        this.linkUnlinkCommand = linkUnlinkCommand;
    }

    @PostMapping
    public User persistUserAndMovies(@RequestBody @Valid User user) {
        return userMovieService.persistUserAndMovies(user);
    }

    @PostMapping("/like/{user}/{movie}")
    @Validated
    public User like(@PathVariable("user") @Email String username, @PathVariable("movie") String movie) {
        return linkUnlinkCommand.link(username, movie);
    }

    @DeleteMapping("/like/{user}/{movie}")
    @Validated
    public User dislike(@PathVariable("user") @Email String username, @PathVariable("movie") String movie) {
        return linkUnlinkCommand.unlink(username, movie);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleNotFound(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("errorMessage", ex.getMessage());

        return errors;
    }

}
