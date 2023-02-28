package com.example.foodcloud.service.user;

import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.exception.UserNameDuplicateException;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserJoinServiceImplTest {
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;

    @Autowired
    UserJoinServiceImplTest(UserJoinService userJoinService, UserRepository userRepository) {
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
    }

    @Test
    void 회원가입_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Optional<User> optional = userRepository.findByName("test");

        User user = optional.orElseThrow(() ->
                new UsernameNotFoundException("Invalid name")
        );

        assertTrue(optional.isPresent());
        assertThat(userRepository.existsByName("test")).isTrue();
        assertEquals("test", user.getName());
        assertNotEquals("test",user.getPassword());
        assertThat(user.getPhone()).isEqualTo("test");
    }

    @Test
    void 회원가입_중복아이디() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        JoinServiceDto joinServiceDto2 = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        UserNameDuplicateException e = assertThrows(UserNameDuplicateException.class, () ->
                userJoinService.join(joinServiceDto2)
        );

        assertEquals("Duplicate User Name", e.getMessage());
    }
}