package com.example.foodcloud.domain.user.domain;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 13)
    private String phone;

    public User() {
    }

    public User(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public void update(String phone) {
        this.phone = phone;
    }

    public boolean isEncodePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
        return passwordEncoder.matches(password, this.password);
    }

    public boolean isCheckPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}

