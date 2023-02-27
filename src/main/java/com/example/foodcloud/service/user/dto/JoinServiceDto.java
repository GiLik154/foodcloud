package com.example.foodcloud.service.user.dto;

import lombok.Getter;

@Getter
public class JoinServiceDto {
    private String name;
    private String password;
    private String phone;
    private Long restaurantID;

    public JoinServiceDto(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public JoinServiceDto(String name, String password, String phone, Long restaurantID) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.restaurantID = restaurantID;
    }
}
