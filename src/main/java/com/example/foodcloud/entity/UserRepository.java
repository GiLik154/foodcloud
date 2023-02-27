package com.example.foodcloud.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    default User findUser(String name) {
        Optional<User> optionalUser = findByName(name);

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid name")
        );
    }

    default User findUser(Long id) {
        Optional<User> optionalUser = findById(id);

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid name")
        );
    }

    Optional<User> findByName(String name);
}
