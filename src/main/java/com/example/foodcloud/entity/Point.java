package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
public class Point {
    @Id
    private Long id;
    private int calculation;
    private int point;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

}
