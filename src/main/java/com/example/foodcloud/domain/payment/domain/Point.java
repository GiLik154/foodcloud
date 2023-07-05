package com.example.foodcloud.domain.payment.domain;

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
     * 현재 남은 포인트
     **/
    @Column(nullable = false)
    private int totalPoint;

    /**
     * 최근 결제 가격
     **/
    private int recentPoint;

    protected Point() {
    }

    /**
     * 포인트의 기본 생성자
     **/
    public Point(User user, PaymentCode paymentCode) {
        super(user, paymentCode);
    }

    /**
     * 포인트를 추가할 때,
     * 검증 후에 추가됨.
     **/
    public void update(int point) {
        valid(point);

        this.recentPoint = point;
        this.totalPoint = Math.addExact(this.totalPoint, point);
    }

    private void valid(int point) {
        if (Math.addExact(this.totalPoint, point) < 0) {
            throw new NotEnoughPointException("Points are less than zero.");
        }
    }
}
