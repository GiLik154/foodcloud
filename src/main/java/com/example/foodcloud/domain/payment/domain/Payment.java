package com.example.foodcloud.domain.payment.domain;


import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * 결제수단의 부모 클래스
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public class Payment {
    /**
     * PK (자동생성)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 낙관적 락을 위한 version
     */
    @Version
    private Long version;

    /**
     * 유저는 여러개의 결제수단을 가질 수 있다.
     * 따라서 ManyToOne으로 객체를 받아와야 함.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 결제수단의 코드
     * (ex. 000 = 포인트, 004 = 농협)
     */
    @Column(nullable = false)
    private PaymentCode paymentCode;

    protected Payment() {
    }

    /**
     * payment 클래스에 기본적으로 필요한
     * 정보들 초기화
     */
    public Payment(User user, PaymentCode paymentCode) {
        this.user = user;
        this.paymentCode = paymentCode;
    }

    /**
     * 결제수단을 바꿀 경우를 대비한
     * update 메소드
     */
    public void updatePaymentCode(PaymentCode paymentCode) {
        this.paymentCode = paymentCode;
    }
}