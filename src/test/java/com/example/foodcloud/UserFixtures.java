package com.example.foodcloud;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.RecordApplicationEvents;

public class UserFixtures {
    private String name = "testName";
    private String password = "testPassword";
    private String phone = "testPhone";


    public static UserFixtures anUserFixtures() {
        return new UserFixtures();
    }

    public UserFixtures name(String name) {
        this.name = name;
        return this;
    }

    public UserFixtures password(String password) {
        this.password = password;
        return this;
    }

    public UserFixtures phone(String phone) {
        this.phone = phone;
        return this;
    }

    public User build() {
        return new User(this.name, this.password, this.phone);
    }
}
