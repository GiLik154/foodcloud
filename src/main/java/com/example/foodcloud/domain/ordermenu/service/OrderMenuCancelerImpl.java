package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuCancelerImpl implements OrderMenuCanceler{
    private final OrderMenuRepository repository;
    private final Map<String, PaymentService> paymentServiceMap;

    @Override
    public String cancel(Long userId, Long orderMenuId) {
        OrderMenu orderMenu = repository.findByUserIdAndId(userId, orderMenuId).orElseThrow(NotFoundOrderMenuException::new);

        orderMenu.updateResult(OrderResult.CANCELED);

        PaymentService paymentService = paymentServiceMap.get(orderMenu.getPayment().getPaymentCode().getCode());

        return paymentService.refund(userId, orderMenu);
    }
}