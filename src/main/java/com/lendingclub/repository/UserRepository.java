package com.lendingclub.repository;

import com.lendingclub.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.validation.Valid;
import java.util.Optional;

@RepositoryRestResource(path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "SELECT u FROM User u LEFT JOIN fetch u.friends f WHERE u.username = ?1")
    Optional<User> findByUsernameWithFriends(@Param("username") String username);

}
