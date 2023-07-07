package com.example.foodcloud.domain.user.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    void deleteByName(String name);

    Optional<User> findByName(String name);
}
