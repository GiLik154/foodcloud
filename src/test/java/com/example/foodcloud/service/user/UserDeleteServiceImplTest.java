package com.example.foodcloud.service.user;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDeleteServiceImplTest {
    private final UserDeleteService userDeleteService;
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;


    @Autowired
    UserDeleteServiceImplTest(UserDeleteService userDeleteService, UserJoinService userJoinService, UserRepository userRepository) {
        this.userDeleteService = userDeleteService;
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
    }


    @Test
    void 삭제_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Long id = userRepository.findByName("test").get().getId();

        userDeleteService.delete(id, "test");

        assertThat(userRepository.existsById(id)).isFalse();
        assertThrows(UsernameNotFoundException.class, () ->
                userRepository.findUser(id)
        );
    }

    @Test
    void 삭제_id_다름() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Long id = userRepository.findByName("test").get().getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                userDeleteService.delete(id + 1L, "test")
        );

        assertThat(userRepository.existsById(id)).isTrue();
        assertEquals("Invalid name", e.getMessage());
    }

    @Test
    void 삭제_pw_다름() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Long id = userRepository.findByName("test").get().getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                userDeleteService.delete(id, "test123")
        );

        assertThat(userRepository.existsById(id)).isTrue();
        assertEquals("Invalid password", e.getMessage());
    }
}