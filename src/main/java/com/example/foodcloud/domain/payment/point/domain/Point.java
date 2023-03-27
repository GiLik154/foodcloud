package com.example.foodcloud.domain.payment.point.domain;

import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.exception.NotEnoughPointException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
public class Point implements Payment {
    public static final int MAX_POINT = 3000000;
    public static final int MIN_POINT = -3000000;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;

    @Min(value = MIN_POINT, message = "Out of bounds for min point")
    @Max(value = MAX_POINT, message = "Out of bounds for max point")
    private int calculation;

    @Column(nullable = false)
    private int totalPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Point() {
    }

    public Point(User user) {
        this.user = user;
    }

    public Point(int calculation, int totalPoint, User user) {
        this.calculation = calculation;
        this.totalPoint = totalPoint;
        this.user = user;
    }

    public void updatePoint(int calculationPoints) {
        if (Math.addExact(this.totalPoint, calculationPoints) < 0) {
            throw new NotEnoughPointException("Points are less than zero.");
        }
        this.calculation = calculationPoints;
        this.totalPoint = Math.addExact(this.totalPoint, calculationPoints);
    }

    @Override
    public void orderMenuUpdate(OrderMenu orderMenu) {
        orderMenu.updatePayment(this);
    }
}
