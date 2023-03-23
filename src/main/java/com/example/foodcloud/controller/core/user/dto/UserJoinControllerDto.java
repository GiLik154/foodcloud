package com.example.foodcloud.controller.core.user.dto;

import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
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

    public UserJoinServiceDto convert() {
        return new UserJoinServiceDto(this.joinName, this.joinPassword, this.joinPhone);
    }
}
