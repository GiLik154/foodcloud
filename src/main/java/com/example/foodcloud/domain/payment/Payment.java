package com.example.foodcloud.domain.payment;


import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private PaymentCode paymentCode;

    protected Payment() {
    }

    public void init(User user, PaymentCode paymentCode) {
        this.user = user;
        this.paymentCode = paymentCode;
    }

    public void updatePaymentCode(PaymentCode paymentCode) {
        this.paymentCode = paymentCode;
    }
}
