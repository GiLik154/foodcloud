package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
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
}