package com.example.foodcloud.domain.user.service.update;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {
    private final UserRepository userRepository;

    @Override
    public boolean update(Long userId, String phone) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            user.update(phone);

            return true;
        }
        return false;
    }
}