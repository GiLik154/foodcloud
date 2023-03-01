package com.example.foodcloud.domain.user.service.validate;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserValidateServiceImpl implements UserValidateService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void validate(String name, String password) {
        User user = userRepository.validateUser(name);

        if (!user.isCheckPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public void validate(Long userId, String password) {
        User user = userRepository.validateUser(userId);

        if (!user.isCheckPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
    }
}
