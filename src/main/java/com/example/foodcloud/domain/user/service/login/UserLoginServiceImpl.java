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

    /**
     * 유저의 이름을 통해 아이디가 있는지 있는지 검증 후
     * 유저의 비밀번호가 일치하는지 확인한다.
     * 일치하지 않을 시 BadCredentialsException이 발생한다.
     * 일치하는 경우 로그인 성공시 유저의 고유키를 리턴한다.
     *
     * @param name     유저가 입력한 이름
     * @param password 유저가 입력한 패스워드
     */
    @Override
    public Long login(String name, String password) {
        User user = userRepository.validate(name);

        if (!user.isValidPassword(bCryptPasswordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }

        return user.getId();
    }
}
