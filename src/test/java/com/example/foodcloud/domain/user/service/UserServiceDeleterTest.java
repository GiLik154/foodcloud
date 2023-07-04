package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceDeleterTest {
    private final UserDeleter userDeleter;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    UserServiceDeleterTest(UserDeleter userDeleter, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userDeleter = userDeleter;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Test
    void 유저_삭제_정상작동() {
        User user = new User("testName", bCryptPasswordEncoder.encode("test"), "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        userDeleter.delete("testName", "test");

        assertFalse(userRepository.existsById(userId));
        assertFalse(userRepository.existsByName("testName"));
    }

    @Test
    void 유저의_name이_다르면_익셉션이_발생함() {
        User user = new User("testName", bCryptPasswordEncoder.encode("test"), "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        assertThrows(UsernameNotFoundException.class, () -> userDeleter.delete("wrongName", "test"));

        assertTrue(userRepository.existsById(userId));
    }

    @Test
    void 유저의_pw가_다르면_익셉션이_발생함() {
        User user = new User("testName", bCryptPasswordEncoder.encode("test"), "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        assertThrows(BadCredentialsException.class, () -> userDeleter.delete("testName","wrongPassword"));

        assertTrue(userRepository.existsById(userId));
    }
}