package com.example.foodcloud.domain.user.service.update;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {
    private final UserRepository userRepository;

    @Override
    public void update(Long userId, String phone) {
        User user = userRepository.validateUser(userId);

        user.update(phone);
    }
}