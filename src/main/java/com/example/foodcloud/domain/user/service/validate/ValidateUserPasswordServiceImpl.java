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

    /** 유저 패스워드 검증 메소드
     * 패스워드 불일치시 익셉션 발생함. */
    @Override
    public void validate(String name, String password) {
        User user = userRepository.validateUser(name);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }
    @Override
    public void validate(Long userId, String password) {
        User user = userRepository.validateUser(userId);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }
}
