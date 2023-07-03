package com.example.foodcloud.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserGrade implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
