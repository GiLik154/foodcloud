package com.example.foodcloud.domain.user.service.validate;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ValidateUserPasswordService {
    /**
     * 유저의 아이디로 유저가 존재하는지 확인한다. (Long or String)
     * 존재 할 경우 유저의 아이디로 패스워드가 일치하는지 검증한다.
     *
     * @param name     유저의 id다
     * @param password 유저의 패스워드
     * @throws UsernameNotFoundException 유저의 아이디로 유저가 존재하는지
     *                                   확인할 때 존재하지 않으면 발생
     * @throws BadCredentialsException   유저의 아이디로 패스워드를 일치하는지
     *                                   검증할 때 일치하지 않으면 발생
     */
    void validate(String name, String password);

    void validate(Long userId, String password);
}
