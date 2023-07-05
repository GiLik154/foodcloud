package com.example.foodcloud.domain.payment.domain;

import com.example.foodcloud.UserFixtures;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.exception.NotEnoughPointException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private final User user = UserFixtures.fixtures().build();

    @Test
    void 최상의_생성_테스트() {
        Point point = new Point(user);

        assertEquals(PaymentCode.POINT, point.getPaymentCode());
        assertEquals(user, point.getUser());
        assertEquals(0, point.getTotalPoint());
    }

    @Test
    void 입금_테스트() {
        Point point = new Point(user);

        point.update(1000);

        assertEquals(1000, point.getRecentPoint());
        assertEquals(1000, point.getTotalPoint());
    }

    @Test
    void 입금_테스트_0보다_작으면_익셉션_발생() {
        Point point = new Point(user);
        point.update(1000);

        assertThrows(NotEnoughPointException.class, () -> point.update(-2000));
    }

    @Test
    void 입금_테스트_오버플로우_발생하면_익셉션_빨생() {
        Point point = new Point(user);
        point.update(5000);

        assertThrows(ArithmeticException.class, () -> point.update(Integer.MAX_VALUE));
    }
}