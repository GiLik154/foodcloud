package com.example.foodcloud.domain.user.service.validate;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserValidationImpl implements UserValidation {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void validate(String name, String password) {
        User user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        validPassword(user, password);
    }

    @Override
    public void validate(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        validPassword(user, password);
    }

    private void validPassword(User user, String password) {
        if (!user.isValidPassword(bCryptPasswordEncoder, password)) throw new BadCredentialsException("Invalid password");
    }
}