package com.example.foodcloud.domain.user.service.delete;

import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService {
    private final UserRepository userRepository;
    private final ValidateUserService validateUserService;

    @Override
    public void delete(Long userId, String name, String password) {
        validateUserService.validate(name, password);

        userRepository.deleteById(userId);
    }
}
