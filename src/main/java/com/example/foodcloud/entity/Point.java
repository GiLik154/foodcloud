package com.example.foodcloud.entity;

import com.example.foodcloud.exception.OutOfBoundsPointException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
public class Point {
    public static final int MAX_POINT = 3000000;
    public static final int MIN_POINT = -3000000;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = MIN_POINT, message = "Out of bounds for min point")
    @Max(value = MAX_POINT, message = "Out of bounds for max point")
    private int calculationPoints;

    @Column(nullable = false)
    private int totalPoint;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Point() {
    }

    public Point(User user) {
        this.user = user;
    }

    public void validatePoint(int calculationPoints) {
        if (Math.addExact(this.totalPoint, calculationPoints) < 0) {
            throw new OutOfBoundsPointException("Points are less than zero.");
        }
        this.calculationPoints = calculationPoints;
        this.totalPoint = Math.addExact(this.totalPoint, calculationPoints);
    }
}
