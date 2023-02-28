package com.example.foodcloud.service.user;

import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void delete(Long id, String password) {
        User user = userRepository.findUser(id);
        if (!user.comparePassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
        userRepository.deleteById(id);
    }
}
