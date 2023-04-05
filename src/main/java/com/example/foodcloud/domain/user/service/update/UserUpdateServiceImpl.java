package com.example.foodcloud.domain.user.service.update;

import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {
    private final UserRepository userRepository;

    /**
     * 유저의 ID로 해당 유저가 존재하는지 확인한다.
     * 존재하는 경우 휴대폰 번호를 변경할 수 있다.
     *
     * @param userId 유저의 아이디
     * @param phone  유저의 휴대폰 번호
     */
    @Override
    public void update(Long userId, String phone) {
        userRepository.findById(userId).ifPresent(user ->
                user.update(phone)
        );
    }
}