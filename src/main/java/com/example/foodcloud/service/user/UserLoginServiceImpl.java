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
public class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public Long login(String name, String password) {
        User user = userRepository.findUser(name);

        if (checkComparePw(user, password)) {
            return user.getId();
        }

        throw new BadCredentialsException("Invalid password");
    }

    private boolean checkComparePw(User user, String password) {
        return user.comparePassword(bCryptPasswordEncoder, password);
    }
}
