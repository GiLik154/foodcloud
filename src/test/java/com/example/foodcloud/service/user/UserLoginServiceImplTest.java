package com.example.foodcloud.service.user;

import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserLoginServiceImplTest {
    private final UserLoginService userLoginService;
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;

    @Autowired
    UserLoginServiceImplTest(UserLoginService userLoginService, UserJoinService userJoinService, UserRepository userRepository) {
        this.userLoginService = userLoginService;
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
    }


    @Test
    void 로그인_정상작동() {
        //given
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        User user = userRepository.findByName("test").orElseThrow(() ->
                new UsernameNotFoundException("Invalid name")
        );

        //when
        userLoginService.login("test", "test");

        //then
        assertEquals("test", user.getName());
        assertNotEquals("test", user.getPassword());
        assertEquals("test", user.getPhone());
    }

    @Test
    void 로그인_아이디_다름() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                userLoginService.login("test123", "test")
        );

        assertEquals("Invalid name", e.getMessage());
    }

    @Test
    void 로그인_비밀번호_다름() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                userLoginService.login("test", "test123")
        );

        assertEquals("Invalid password", e.getMessage());
    }

    @Test
    void 로그인_아이디_비밀번호_다름() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                userLoginService.login("test123", "test123")
        );

        assertEquals("Invalid name", e.getMessage());
    }
}