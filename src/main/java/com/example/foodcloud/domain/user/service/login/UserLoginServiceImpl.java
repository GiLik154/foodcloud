package com.example.foodcloud.domain.user.service.login;

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
public class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long login(String name, String password) {
        User user = userRepository.validateUser(name);

        if (!user.isCheckPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }

        return user.getId();
    }
}
