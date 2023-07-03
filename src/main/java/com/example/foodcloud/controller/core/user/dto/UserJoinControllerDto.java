package com.example.foodcloud.controller.core.user.dto;

import com.example.foodcloud.domain.user.service.commend.UserJoinerCommend;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserJoinControllerDto {
    @NotBlank(message = "Username is required")
    private final String joinName;
    @NotBlank(message = "Password is required")
    private final String joinPassword;
    @NotBlank
    private final String joinPhone;

    public UserJoinControllerDto(String joinName, String joinPassword, String joinPhone) {
        this.joinName = joinName;
        this.joinPassword = joinPassword;
        this.joinPhone = joinPhone;
    }

    public UserJoinerCommend convert() {
        return new UserJoinerCommend(this.joinName, this.joinPassword, this.joinPhone);
    }
}
