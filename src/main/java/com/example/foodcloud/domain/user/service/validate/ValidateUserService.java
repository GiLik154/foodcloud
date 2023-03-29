package com.example.foodcloud.domain.user.service.validate;

public interface ValidateUserService {
    void validate(String name, String password);
    void validate(Long userId, String password);
}
