package com.example.foodcloud.domain.order.join.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderJoinGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String time;
    private OrderResult result;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;


    public OrderJoinGroup(){}
    public OrderJoinGroup(String location, String time, User user, Restaurant restaurant) {
        this.location = location;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
        this.result = OrderResult.PAYMENT_WAITING;
    }

    public void updateResult(OrderResult result){
        this.result = result;
    }
}
