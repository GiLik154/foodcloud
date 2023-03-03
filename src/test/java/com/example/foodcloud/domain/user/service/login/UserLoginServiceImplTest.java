package com.example.foodcloud.domain.user.service.login;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.login.UserLoginService;
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
class UserLoginServiceImplTest {
    private final UserLoginService userLoginService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserLoginServiceImplTest(UserLoginService userLoginService, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userLoginService = userLoginService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 로그인_정상작동() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        Long userId = user.getId();
        Long loginId = userLoginService.login("test", "test");

        assertEquals(loginId, userId);
    }

    @Test
    void 로그인_아이디_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                userLoginService.login("test123", "test")
        );

        assertEquals("Invalid user", e.getMessage());
    }

    @Test
    void 로그인_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                userLoginService.login("test", "test123")
        );

        assertEquals("Invalid password", e.getMessage());
    }

    @Test
    void 로그인_아이디_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                userLoginService.login("test123", "test123")
        );

        assertEquals("Invalid user", e.getMessage());
    }
}