package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.point.service.sum.PointSumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("point")
@RequiredArgsConstructor
public class PointPaymentServiceImpl implements PaymentService {
    private final PointSumService pointSumService;

    @Override
    public String payment(Long userId, Long bankAccountId, int price) {
        if (pointSumService.sum(userId, price)) {
            return price + " price Point payment succeed";
        }
        return "Point payment fail";
    }
}
