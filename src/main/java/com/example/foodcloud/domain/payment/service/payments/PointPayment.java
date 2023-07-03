package com.example.foodcloud.domain.payment.service.payments;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointCalculator;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("000")
@RequiredArgsConstructor
public class PointPayment implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final PointCalculator pointCalculator;
    private final PointRepository pointRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (pointRepository.existsByUserId(userId)) {
            pointCalculator.sum(userId, price * -1);

            orderMenuPaymentUpdateService.update(orderMenuId, getPoint(userId));

            return price + " price Point payment succeed";
        }
        return "Point payment fail";
    }

    @Override
    public String refund(Long userId, OrderMenu orderMenu) {
        if (pointRepository.existsByUserId(userId)) {
            pointCalculator.sum(userId, orderMenu.getPrice());
            return orderMenu.getPrice() + " price Point refund succeed";
        }
        return "Point refund fail";
    }

    private Point getPoint(Long userId) {
        return pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);
    }
}
