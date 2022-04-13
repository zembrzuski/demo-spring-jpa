package com.lendingclub.model.entity;

import com.google.common.collect.Sets;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "user_entity",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"fullName"}),
    })
@EqualsAndHashCode(exclude="friends")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private Long version;

    @NotEmpty
    @Email(message = "Your username should be a valid email")
    private String username;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @Valid
    private Set<Movie> movies;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> friends;

    @ManyToOne
    @Valid
    private Address address;

    public User(String username) {
        this.username = username;
    }

    public void addMovie(Movie movie) {
        if (movie == null) {
            movies = Sets.newHashSet();
        }
        movies.add(movie);
    }

    public void addFriend(User user) {
        if (friends == null) {
            friends = Sets.newHashSet();
        }
        friends.add(user);
    }

}
