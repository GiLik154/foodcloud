package com.example.foodcloud.domain.user.service.delete;

import org.springframework.security.authentication.BadCredentialsException;

public interface UserDeleteService {
    /**
     * 유저의 아이디를 통해 패스워드가 일치하는지 검증한다
     * 검증에 성공하면 유저의 아이디를 통해 삭제한다
     *
     * @param name     유저의 아이디
     * @param password 유저가 입력한 패스워드
     * @throws BadCredentialsException 유저의 아이디를 통해
     *                                 패스워드 검증에 실패했을 때 발생
     */
    void delete(String name, String password);
}
