package com.example.foodcloud.domain.user.domain;

import com.example.foodcloud.enums.UserGrade;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 유저의 StringId 유니크화 시켜서 중복으로 들어갈 수 없게 만듬 */
    @Column(nullable = false, length = 20, unique = true)
    private String name;

    /** 유저의 패스워드. 비크립트를 통해 암호화 함. */
    @Column(nullable = false)
    private String password;

    /** 유저의 휴대폰 번호 */
    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserGrade userGrade;

    protected User() {}

    /** 유저의 기본 생성자 */
    public User(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.userGrade = UserGrade.USER;
    }

    /** 유저의 패스워드를 암호화. */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    /** 휴대폰 번호를 업데이트 함 */
    public void update(String phone) {
        this.phone = phone;
    }

    public void update(UserGrade userGrade) {
        this.userGrade = userGrade;
    }

    /** 패스워드를 검증하는 매소드 */
    public boolean isValidPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}