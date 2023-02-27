package com.example.foodcloud.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Point {
    @Id
    private Long id;
    private int calculationPoints;
    @Column(nullable = false)
    private int totalPoint;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Point() {
    }

    public Point(int totalPoint, User user) {
        this.totalPoint = totalPoint;
        this.user = user;
    }

    public void sumPoint(int calculationPoints) {
        this.calculationPoints = Math.addExact(this.totalPoint, calculationPoints);
        if (this.calculationPoints < 0) {
            throw new IllegalArgumentException("Not enough point");
        }
    }
}
