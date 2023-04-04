package com.example.foodcloud.domain.user.service.join;

import com.example.foodcloud.domain.payment.point.service.award.PointAwardService;
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
    private final PointAwardService pointAwardService;

    /** 유저 생성 메소드
     * 유저의 정보를 JoinServiceDto로 받아온 후
     * 유저의 아이디를 중복체크 함.
     * bCryptPasswordEncoder는 개발자가 쉽게 컨트롤 할 수 없기 때문에 메소드로 따로 작성함
     * 유저의 생성에 성공하면 포인트도 생성해야 하기 때문에
     * 유저의 고유 번호를 통해 포인트 DB를 생성함. */
    public void join(UserJoinServiceDto userJoinServiceDto) {
        User user = new User(userJoinServiceDto.getName(), userJoinServiceDto.getPassword(), userJoinServiceDto.getPhone());

        checkDuplicate(userJoinServiceDto.getName());

        user.encodePassword(bCryptPasswordEncoder);

        userRepository.save(user);

        pointAwardService.award(user.getId());
    }

    private void checkDuplicate(String name) {
        if (userRepository.existsByName(name)) {
            throw new UserNameDuplicateException();
        }
    }
}
