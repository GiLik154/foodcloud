package com.example.foodcloud.domain.user.service.join.dto;

import lombok.Getter;

@Getter
public class UserJoinServiceDto {
    /**
     * 유저의 StringId
     */
    private final String name;
    /**
     * 유저의 비밀번호
     */
    private final String password;
    /**
     * 유저의 휴대폰 번호
     */
    private final String phone;

    public UserJoinServiceDto(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
}
