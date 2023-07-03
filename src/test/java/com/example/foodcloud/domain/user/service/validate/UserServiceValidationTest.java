package com.example.foodcloud.domain.user.service.validate;

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
class UserServiceValidationTest {
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceValidationTest(UserValidation userValidation, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userValidation = userValidation;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 유저_아이디_검증() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        userValidation.validate("test", "test");
    }

    @Test
    void 유저_아이디_아이디_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        assertThrows(UsernameNotFoundException.class, () -> userValidation.validate("test123", "test"));
    }

    @Test
    void 유저_아이디_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        assertThrows(BadCredentialsException.class, () -> userValidation.validate("test", "test123"));
    }

    @Test
    void 유저_고유번호_검증() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        userValidation.validate(userId, "test");
    }

    @Test
    void 유저_고유번호_아이디_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        assertThrows(UsernameNotFoundException.class, () -> userValidation.validate(userId + 1L, "test"));
    }

    @Test
    void 유저_고유번호_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        assertThrows(BadCredentialsException.class, () -> userValidation.validate(userId, "test123"));
    }
}