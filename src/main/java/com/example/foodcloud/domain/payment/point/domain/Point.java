package com.example.foodcloud.domain.payment.point.domain;

import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.exception.NotEnoughPointException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
public class Point extends Payment {
    private int calculation;

    @Column(nullable = false)
    private int totalPoint;

    protected Point() {
    }

    public Point(User user, PaymentCode paymentCode) {
        updateUser(user);
        updatePaymentCode(paymentCode);
    }
    
    public Point(int calculation, int totalPoint) {
        this.calculation = calculation;
        this.totalPoint = totalPoint;
    }

    public void updatePoint(int calculationPoints) {
        if (Math.addExact(this.totalPoint, calculationPoints) < 0) {
            throw new NotEnoughPointException("Points are less than zero.");
        }
        this.calculation = calculationPoints;
        this.totalPoint = Math.addExact(this.totalPoint, calculationPoints);
    }
}
