package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.UserLogin;
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
class UserServiceLoginTest {
    private final UserLogin userLogin;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserServiceLoginTest(UserLogin userLogin, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userLogin = userLogin;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 로그인_정상작동() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        Long userId = user.getId();
        Long loginId = userLogin.login("test", "test");

        assertEquals(loginId, userId);
    }

    @Test
    void 로그인_아이디_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        assertThrows(UsernameNotFoundException.class, () -> userLogin.login("test123", "test"));
    }

    @Test
    void 로그인_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                userLogin.login("test", "test123")
        );

        assertEquals("Invalid password", e.getMessage());
    }

    @Test
    void 로그인_아이디_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        assertThrows(UsernameNotFoundException.class, () -> userLogin.login("test123", "test123"));
    }
}