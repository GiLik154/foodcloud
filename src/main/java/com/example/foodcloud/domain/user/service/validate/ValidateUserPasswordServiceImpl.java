package com.example.foodcloud.domain.user.service.validate;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ValidateUserPasswordServiceImpl implements ValidateUserPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * 유저로 유저가 존재하는지 확인한다. (Long or String)
     * 존재하지 않을 경우 UsernameNotFoundException 익셉션이 발생한다.
     * 존재 할 경우 패스워드를 비교한다.
     * 패스워드가 다를 경우 BadCredentialsException 익셉션이 발생한다.
     *
     * @param name     유저의 id
     * @param password 유저의 패스워드
     */
    @Override
    public void validate(String name, String password) {
        User user = userRepository.validate(name);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public void validate(Long userId, String password) {
        User user = userRepository.validate(userId);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }
}
