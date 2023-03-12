package com.example.foodcloud.domain.user.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    default User validateUser(String name) {
        Optional<User> optionalUser = findByName(name);

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid user")
        );
    }

    default User validateUser(Long userId) {

        Optional<User> optionalUser = findById(userId);

        System.out.println(optionalUser.isPresent() + "이게 트루라고?");

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid user")
        );
    }

    Optional<User> findByName(String name);
}
