package com.example.foodcloud.domain.user.service.join;

import com.example.foodcloud.domain.payment.service.point.PointRegister;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import com.example.foodcloud.exception.UserNameDuplicateException;
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
    private final PointRegister pointRegister;

    public void join(UserJoinServiceDto userJoinServiceDto) {
        User user = new User(userJoinServiceDto.getName(), userJoinServiceDto.getPassword(), userJoinServiceDto.getPhone());

        checkDuplicate(userJoinServiceDto.getName());

        user.encodePassword(bCryptPasswordEncoder);

        userRepository.save(user);

        pointRegister.register(user.getId());
    }

    private void checkDuplicate(String name) {
        if (userRepository.existsByName(name)) {
            throw new UserNameDuplicateException();
        }
    }
}
