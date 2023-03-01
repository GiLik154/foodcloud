package com.example.foodcloud.domain.user.service.join;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.UserNameDuplicateException;
import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import com.example.foodcloud.domain.user.service.join.UserJoinService;
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
    void 유저_회원가입_정상작동() {
        UserJoinServiceDto userJoinServiceDto = new UserJoinServiceDto("test", "test", "test");
        userJoinService.join(userJoinServiceDto);

        Optional<User> optional = userRepository.findByName("test");

        User user = optional.orElseThrow(() ->
                new UsernameNotFoundException("Invalid user")
        );

        assertTrue(optional.isPresent());
        assertThat(userRepository.existsByName("test")).isTrue();
        assertEquals("test", user.getName());
        assertNotEquals("test",user.getPassword());
        assertThat(user.getPhone()).isEqualTo("test");
    }

    @Test
    void 유저_회원가입_중복아이디() {
        UserJoinServiceDto userJoinServiceDto = new UserJoinServiceDto("test", "test", "test");
        UserJoinServiceDto userJoinServiceDto2 = new UserJoinServiceDto("test", "test", "test");
        userJoinService.join(userJoinServiceDto);

        UserNameDuplicateException e = assertThrows(UserNameDuplicateException.class, () ->
                userJoinService.join(userJoinServiceDto2)
        );

        assertEquals("Duplicate User Name", e.getMessage());
    }
}