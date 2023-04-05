package com.example.foodcloud.domain.user.service.delete;

import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService {
    private final UserRepository userRepository;
    private final ValidateUserPasswordService validateUserPasswordService;

    @Override
    public void delete(String name, String password) {
        validateUserPasswordService.validate(name, password);

        userRepository.deleteByName(name);
    }
}
