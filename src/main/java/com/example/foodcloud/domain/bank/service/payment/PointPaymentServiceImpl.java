package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.order.menu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.point.service.sum.PointSumService;
import com.example.foodcloud.enums.BankCode;
import com.example.foodcloud.enums.PaymentType;
import com.example.foodcloud.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service("000")
@RequiredArgsConstructor
public class PointPaymentServiceImpl implements PaymentService {
    private final Map<String, OrderMenuPaymentUpdateService> updateServiceMap;
    private final PointSumService pointSumService;
    private final PointRepository pointRepository;

    @Override
    public String pay(String bank, Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (pointSumService.sum(userId, price * -1)) {
            OrderMenuPaymentUpdateService orderMenuPaymentUpdateService =
                    updateServiceMap.get(PaymentType.findByPaymentType(
                            BankCode.returnBankCode(bank)));

            orderMenuPaymentUpdateService.isUpdate(orderMenuId, getPointId(userId));

            return price + " price Point payment succeed";
        }
        return "Point payment fail";
    }

    @Override
    public String refund(Long userId, Long orderMenuId, Long bankAccountId, int price) {
        if (pointSumService.sum(userId, price)) {
            return price + " price Point refund succeed";
        }
        return "Point refund fail";
    }

    private Point getPointId(Long userId) {
        return pointRepository.findByUserId(userId).orElseThrow(NotFoundPointException::new);
    }
}
