package com.example.foodcloud.domain.user.service.join;

import com.example.foodcloud.domain.payment.point.service.award.PointAwardService;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import com.example.foodcloud.exception.UserNameDuplicateException;
import com.example.foodcloud.exception.PasswordEncodingFailedException;
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
    private final PointAwardService pointAwardService;

    public void join(UserJoinServiceDto userJoinServiceDto) {
        User user = new User(userJoinServiceDto.getName(), userJoinServiceDto.getPassword(), userJoinServiceDto.getPhone());

        checkDuplicate(userJoinServiceDto.getName());

        checkEncodingPw(user, userJoinServiceDto.getPassword());

        userRepository.save(user);

        pointAwardService.award(user.getId(), 0);
    }

    private void checkDuplicate(String name) {
        if (userRepository.existsByName(name)) {
            throw new UserNameDuplicateException();
        }
    }

    private void checkEncodingPw(User user, String password) {
        if (!user.isEncodePassword(bCryptPasswordEncoder, password)) {
            throw new PasswordEncodingFailedException();
        }
    }
}
