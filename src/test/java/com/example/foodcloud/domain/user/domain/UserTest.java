package com.example.foodcloud.domain.user.domain;

import com.example.foodcloud.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserTest(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 최상의_생성_테스트() {
        User user = new User("testName", "testPassword", "testPhone");

        assertEquals("testName", user.getName());
        assertEquals("testPassword", user.getPassword());
        assertEquals("testPhone", user.getPhone());
        assertEquals(UserGrade.USER, user.getUserGrade());
    }

    @Test
    void 비밀번호_인코딩_테스트() {
        User user = new User("testName", "testPassword", "testPhone");

        user.encodePassword(bCryptPasswordEncoder);

        assertTrue(bCryptPasswordEncoder.matches("testPassword", user.getPassword()));
    }

    @Test
    void 휴대폰_업데이트_테스트(){
        User user = new User("testName", "testPassword", "testPhone");

        user.update("newPhone");

        assertEquals("newPhone", user.getPhone());
    }

    @Test
    void 비밀번호_인증_테스트_비밀번호가_일치하면_true_반환() {
        User user = new User("testName", "testPassword", "testPhone");

        user.encodePassword(bCryptPasswordEncoder);

        assertTrue(user.isValidPassword(bCryptPasswordEncoder, "testPassword"));
    }

    @Test
    void 비밀번호_인증_테스트_비밀번호가_다르면_false_반환() {
        User user = new User("testName", "testPassword", "testPhone");

        user.encodePassword(bCryptPasswordEncoder);

        assertFalse(user.isValidPassword(bCryptPasswordEncoder, "wrongPassword"));
    }
}