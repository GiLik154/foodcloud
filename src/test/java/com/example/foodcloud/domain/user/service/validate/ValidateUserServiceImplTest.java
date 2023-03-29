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
class ValidateUserServiceImplTest {
    private final ValidateUserService validateUserService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ValidateUserServiceImplTest(ValidateUserService validateUserService, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.validateUserService = validateUserService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 유저_아이디_검증() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        validateUserService.validate("test", "test");
    }

    @Test
    void 유저_아이디_아이디_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                validateUserService.validate("test123", "test")
        );

        assertEquals("Invalid user", e.getMessage());
    }

    @Test
    void 유저_아이디_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                validateUserService.validate("test", "test123")
        );

        assertEquals("Invalid password", e.getMessage());
    }

    @Test
    void 유저_고유번호_검증() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        validateUserService.validate(userId, "test");
    }

    @Test
    void 유저_고유번호_아이디_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                validateUserService.validate(userId + 1L, "test")
        );

        assertEquals("Invalid user", e.getMessage());
    }

    @Test
    void 유저_고유번호_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                validateUserService.validate(userId, "test123")
        );

        assertEquals("Invalid password", e.getMessage());
    }
}