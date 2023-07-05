package com.example.foodcloud;

import com.example.foodcloud.domain.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFixture {
    private String name = "testUserName";
    private String password = "testUserPassword";
    private String phone = "testUserPhone";


    public static UserFixture fixture() {
        return new UserFixture();
    }

    public UserFixture name(String name) {
        this.name = name;
        return this;
    }

    public UserFixture password(String password) {
        this.password = password;
        return this;
    }

    public UserFixture encoding(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
        return this;
    }

    public UserFixture phone(String phone) {
        this.phone = phone;
        return this;
    }

    public User build() {
        return new User(this.name, this.password, this.phone);
    }
}
