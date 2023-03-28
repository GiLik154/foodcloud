package com.example.foodcloud.domain.order.main.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
public class OrderMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String time;
    private String result;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    public OrderMain(){}
    public OrderMain(String location, String time, User user, Restaurant restaurant) {
        this.location = location;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
        this.result = "Payment waiting";
    }

    public void updateResult(String result){
        this.result = result;
    }
}
