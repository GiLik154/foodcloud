package com.example.foodcloud.domain.user.service.join.dto;

import lombok.Getter;

@Getter
public class UserJoinServiceDto {
    private final String name;
    private final String password;
    private final String phone;

    public UserJoinServiceDto(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
}
