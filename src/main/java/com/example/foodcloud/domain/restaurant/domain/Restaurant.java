package com.example.foodcloud.domain.restaurant.domain;

import com.example.foodcloud.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 식당의 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 식당의 장소
     */
    @Column(nullable = false)
    private String location;

    /**
     * 식당 운영 시간
     */
    @Column(length = 11)
    private String businessHours;

    /**
     * 식당 주문 횟수
     */
    @Column(nullable = false)
    private int orderCount;

    /**
     * 식당을 등록한 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    protected Restaurant() {
    }

    /**
     * 식당 추가 기본 생성자
     */
    public Restaurant(String name, String location, String businessHours, User user) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
        this.user = user;
    }

    /**
     * 식당 업데이트 메소드
     * (User는 업데이트 할 수 없음)
     */
    public void update(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }

    /**
     * 식당 주문 횟수 ++ 메소드
     */
    public void incrementOrderCount() {
        this.orderCount++;
    }
}