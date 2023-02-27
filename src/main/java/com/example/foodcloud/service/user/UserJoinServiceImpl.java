package com.example.foodcloud.service.user;

import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserJoinServiceImpl implements UserJoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public void isJoin(JoinServiceDto joinServiceDto) {
        User user = new User(joinServiceDto.getName(), joinServiceDto.getPassword(), joinServiceDto.getPhone());
        checkDuplicate(joinServiceDto.getName());
        checkEncodingPw(user, joinServiceDto.getPassword());
        userRepository.save(user);
    }

    private void checkDuplicate(String name) {
        if (userRepository.existsByName(name)) {
            throw new IllegalStateException("Duplicate Name");
        }
    }

    private void checkEncodingPw(User user, String password) {
        if (!user.isEncodePassword(bCryptPasswordEncoder, password)) {
            throw new IllegalStateException("Pw Encoding Failed");
        }
    }
}
