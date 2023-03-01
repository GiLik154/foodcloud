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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private int orderCount;
    @Column(length = 11)
    private String businessHours;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Restaurant() {

    }

    public Restaurant(String name, String location, String businessHours, User user) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
        this.user = user;
    }

    public void update(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }
}