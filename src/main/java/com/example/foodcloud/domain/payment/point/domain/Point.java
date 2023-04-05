package com.example.foodcloud.domain.payment.point.domain;

import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.exception.NotEnoughPointException;
import lombok.Getter;

import javax.persistence.*;

/**
 * 포인트
 **/
@Entity
@Getter
public class Point extends Payment {
    /**
     * 최근 결제 가격
     **/
    private int calculation;

    /**
     * 현재 남은 포인트
     **/
    @Column(nullable = false)
    private int totalPoint;

    protected Point() {
    }

    /**
     * 포인트의 기본 생성자
     **/
    public Point(User user, PaymentCode paymentCode) {
        init(user, paymentCode);
    }

    /**
     * 오버플로우를 막고, 0보다 작아지는 것을 막는다.
     * 0 보다 작아지면 익셉션을 통해 핸들링을 한다.
     **/
    public void updatePoint(int calculationPoints) {
        if (Math.addExact(this.totalPoint, calculationPoints) < 0) {
            throw new NotEnoughPointException("Points are less than zero.");
        }
        this.calculation = calculationPoints;
        this.totalPoint = Math.addExact(this.totalPoint, calculationPoints);
    }
}
