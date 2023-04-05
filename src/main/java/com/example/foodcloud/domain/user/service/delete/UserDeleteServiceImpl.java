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

    /**
     * 유저의 name과 passowrd를 받은 후 검증 후에
     * 유저의 고유번호를 통해 삭제.
     *
     * @param password 유저가 입력한 패스워드
     */
    @Override
    public void delete(Long userId, String name, String password) {
        validateUserPasswordService.validate(name, password);

        userRepository.deleteById(userId);
    }
}
