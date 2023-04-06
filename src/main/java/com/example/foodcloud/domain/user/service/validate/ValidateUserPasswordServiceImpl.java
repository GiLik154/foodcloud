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

    @Override
    public void validate(String name, String password) {
        User user = userRepository.getValidById(name);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public void validate(Long userId, String password) {
        User user = userRepository.getValidById(userId);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }
}
