package com.example.foodcloud.domain.user.service.delete;

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
class UserDeleteServiceImplTest {
    private final UserDeleteService userDeleteService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    UserDeleteServiceImplTest(UserDeleteService userDeleteService, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userDeleteService = userDeleteService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Test
    void 유저_삭제_정상작동() {
        User user = new User("testName", bCryptPasswordEncoder.encode("test"), "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        userDeleteService.delete("testName", "test");

        assertFalse(userRepository.existsById(userId));
        assertThrows(UsernameNotFoundException.class, () ->
                userRepository.getValidById(userId)
        );
    }

    @Test
    void 유저_삭제_id_다름() {
        User user = new User("testName", bCryptPasswordEncoder.encode("test"), "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                userDeleteService.delete("wrongName", "test")
        );

        assertTrue(userRepository.existsById(userId));
        assertEquals("Invalid user", e.getMessage());
    }

    @Test
    void 유저_삭제_pw_다름() {
        User user = new User("testName", bCryptPasswordEncoder.encode("test"), "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                userDeleteService.delete("testName","wrongPassword")
        );

        assertTrue(userRepository.existsById(userId));
        assertEquals("Invalid password", e.getMessage());
    }
}