package com.lendingclub.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "genre"}))
public class Movie {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private Long version;

    @NotBlank(message = "movie name not present")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "movie genre not present")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    public Movie(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }

}
