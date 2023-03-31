package com.example.foodcloud.domain.user.service.login;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    /** 유저 로그인 메소드
     * 유저의 아이디가 있는지 검증 후
     * 유저의 비밀번호가 불일치시 오류 발생.
     * 로그인 성공시 유저의 고유키를 리턴함 */
    @Override
    public Long login(String name, String password) {
        User user = userRepository.validateUser(name);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }

        return user.getId();
    }
}
