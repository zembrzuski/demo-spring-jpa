package com.lendingclub.repository;

import com.lendingclub.model.entity.Genre;
import com.lendingclub.model.entity.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "movie")
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    List<Movie> findByGenre(@Param("genre") Genre genre);

    Optional<Movie> findByName(@Param("name") String name);

    List<Movie> findByNameInOrderByName(@Param("movieNames") Collection<String> movieNames);

}
