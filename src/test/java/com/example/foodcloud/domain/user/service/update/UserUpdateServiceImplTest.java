package com.example.foodcloud.domain.user.service.update;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.update.UserUpdateService;
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
class UserUpdateServiceImplTest {
    private final UserUpdateService userUpdateService;
    private final UserRepository userRepository;

    @Autowired
    public UserUpdateServiceImplTest(UserUpdateService userUpdateService, UserRepository userRepository) {
        this.userUpdateService = userUpdateService;
        this.userRepository = userRepository;
    }

    @Test
    void 유저_업데이트_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        userUpdateService.update(userId, "test123");

        User updateTest = userRepository.findById(userId).get();

        assertEquals("test", updateTest.getName());
        assertEquals("test", updateTest.getPassword());
        assertEquals("test123", updateTest.getPhone());
    }

    @Test
    void 유저_업데이트_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        userUpdateService.update(userId + 1, "test123");

        User updateTest = userRepository.findById(userId).get();

        assertEquals("test", updateTest.getName());
        assertEquals("test", updateTest.getPassword());
        assertNotEquals("test123", updateTest.getPhone());
    }
}