package com.example.foodcloud.domain.user.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    void deleteByName(String name);

    Optional<User> findByName(String name);

    @Override
    Optional<User> findById(Long userId);
}
