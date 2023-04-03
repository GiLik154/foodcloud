package com.example.foodcloud.domain.user.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    /**
     * 유저를 검증하기 위한 메소드. 중복코드를 줄이기 위해 default로 레포지토리 안에 선언함.
     */
    default User validateUser(String name) {
        Optional<User> optionalUser = findByName(name);

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid user")
        );
    }

    Optional<User> findByName(String name);

    default User validateUser(Long userId) {
        Optional<User> optionalUser = findById(userId);

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("Invalid user")
        );
    }

    @Override
    Optional<User> findById(Long aLong);
}
