package com.example.foodcloud.domain.groupbuylist.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class GroupBuyList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 주문한 장소
     */
    @Column(nullable = false)
    private String location;

    /**
     * 주문한 시간
     */
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime time;

    /**
     * 주문한 유저
     */
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 주문한 식당
     */
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    /**
     * 주문한 처리 결과
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderResult result;

    protected GroupBuyList() {
    }

    /**
     * 기본 생성자
     */
    public GroupBuyList(String location, LocalDateTime time, User user, Restaurant restaurant) {
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
