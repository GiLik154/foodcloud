package com.example.foodcloud.domain.payment.bank.service.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.payment.point.service.sum.PointSumService;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("000")
@RequiredArgsConstructor
public class PointPaymentServiceImpl implements PaymentService {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final PointSumService pointSumService;
    private final PointRepository pointRepository;

    @Override
    public String pay(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (pointSumService.sum(userId, price * -1)) {

            orderMenuPaymentUpdateService.update(orderMenuId, getPointId(userId));

            return price + " price Point payment succeed";
        }
        return "Point payment fail";
    }

    @Override
    public String refund(Long userId, OrderMenu orderMenu) {
        if (pointSumService.sum(userId, orderMenu.getPrice())) {
            return orderMenu.getPrice() + " price Point refund succeed";
        }
        return "Point refund fail";
    }

    private Point getPointId(Long userId) {
        return pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);
    }
}
