package com.example.foodcloud.controller.core.user.req;

import com.example.foodcloud.domain.user.service.commend.UserRegisterCommend;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserJoinReq {
    @NotBlank(message = "Username is required")
    private final String joinName;
    @NotBlank(message = "Password is required")
    private final String joinPassword;
    @NotBlank
    private final String joinPhone;

    public UserJoinReq(String joinName, String joinPassword, String joinPhone) {
        this.joinName = joinName;
        this.joinPassword = joinPassword;
        this.joinPhone = joinPhone;
    }

    public UserRegisterCommend convert() {
        return new UserRegisterCommend(this.joinName, this.joinPassword, this.joinPhone);
    }
}
