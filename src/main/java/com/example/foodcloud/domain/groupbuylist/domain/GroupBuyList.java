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

    /** 주문한 장소 */
    @Column(nullable = false)
    private String location;

    /** 주문한 시간 */
    @CreatedDate
    private final LocalDateTime time = LocalDateTime.now();

    /** 주문한 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /** 주문한 식당 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    /** 주문한 처리 결과 */
    @Enumerated(EnumType.STRING)
    private OrderResult result;

    protected GroupBuyList() {
    }

    /** 기본 생성자 */
    public GroupBuyList(String location, User user, Restaurant restaurant) {
        this.location = location;
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
