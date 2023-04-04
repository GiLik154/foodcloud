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

    /** 유저 업데이트 메소드.
     * 유저의 고유키로 유저를 찾은 후
     * 휴대폰 번호만 변경 가능 */
    @Override
    public void update(Long userId, String phone) {
        userRepository.findById(userId).ifPresent(user ->
                user.update(phone)
        );
    }
}