package com.example.foodcloud.domain.user.service.login;

import org.springframework.security.authentication.BadCredentialsException;

public interface UserLoginService {
    /**
     * 유저의 아이디를 통해 아이디가 존재하는지 확인한다.
     * 존재하면 유저의 아이디로 유저의 비밀번호가 일치하는지 확인한다.
     * 일치하는 경우 로그인 성공시 유저의 고유키를 리턴한다.
     *
     * @param name     유저가 입력한 이름
     * @param password 유저가 입력한 패스워드
     * @throws BadCredentialsException   유저의 아이디로 패스워드를 일치하는지
     *                                   검증할 때 일치하지 않으면 발생
     */
    Long login(String name, String password);
}
