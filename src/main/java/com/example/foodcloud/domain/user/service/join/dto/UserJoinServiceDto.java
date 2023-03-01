package com.example.foodcloud.domain.user.service.join.dto;

import lombok.Getter;

@Getter
public class UserJoinServiceDto {
    private String name;
    private String password;
    private String phone;

    public UserJoinServiceDto(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
}
