package com.example.foodcloud.domain.order.join.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Getter
public class OrderJoinGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 주문한 장소
     */
    private String location;
    /**
     * 주문한 시간
     */
    private String time;
    /**
     * 주문한 처리 결과
     */
    private OrderResult result;
    /**
     * 주문한 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private User user;
    /**
     * 주문한 식당
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;


    protected OrderJoinGroup() {
    }

    /**
     * 기본 생성자
     */
    public OrderJoinGroup(String location, String time, User user, Restaurant restaurant) {
        this.location = location;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
        this.result = OrderResult.PAYMENT_WAITING;
    }

    /**
     * 결과 업데이트
     */
    public void updateResult(OrderResult result) {
        this.result = result;
    }
}
