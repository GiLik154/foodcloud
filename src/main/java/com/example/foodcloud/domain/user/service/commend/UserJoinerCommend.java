package com.example.foodcloud.domain.user.service.commend;

import lombok.Getter;

@Getter
public class UserJoinerCommend {
    /** 유저의 이름 */
    private final String username;
    /** 유저의 비밀번호 */
    private final String password;
    /** 유저의 휴대폰 번호 */
    private final String phone;

    public UserJoinerCommend(String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
}
