package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        userRepository.save(UserFixture.fixture().build());

        userUpdater.update("testUserName", "newPhone");

        User updateTest = userRepository.findByName("testName").get();

        assertEquals("testName", updateTest.getName());
        assertEquals("testPassword", updateTest.getPassword());
        assertEquals("newPhone", updateTest.getPhone());
    }

    @Test
    void 유저의_고유변호가_다르면_익셉션_발생() {
        userRepository.save(UserFixture.fixture().build());

        assertThrows(UsernameNotFoundException.class, () ->
                userUpdater.update("wrongName", "test123"));
    }
}