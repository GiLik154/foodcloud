package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceUpdaterTest {
    private final UserUpdater userUpdater;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceUpdaterTest(UserUpdater userUpdater, UserRepository userRepository) {
        this.userUpdater = userUpdater;
        this.userRepository = userRepository;
    }

    @Test
    void 유저_업데이트_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        userUpdater.update("test", "test123");

        User updateTest = userRepository.findById(userId).get();

        assertEquals("test", updateTest.getName());
        assertEquals("test", updateTest.getPassword());
        assertEquals("test123", updateTest.getPhone());
    }

    @Test
    void 유저의_고유변호가_다르면_익셉션_발생() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        userUpdater.update("wrongName", "test123");

        User updateTest = userRepository.findById(userId).get();

        assertEquals("test", updateTest.getName());
        assertEquals("test", updateTest.getPassword());
        assertNotEquals("test123", updateTest.getPhone());
    }
}