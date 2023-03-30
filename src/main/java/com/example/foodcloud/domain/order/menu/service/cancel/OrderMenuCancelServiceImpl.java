package com.example.foodcloud.domain.order.menu.service.cancel;

import com.example.foodcloud.domain.payment.bank.service.payment.PaymentService;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.update.OrderMenuResultUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuCancelServiceImpl implements OrderMenuCancelService {
    private final OrderMenuResultUpdateService orderMenuResultUpdateService;
    private final Map<String, PaymentService> paymentServiceMap;
    private final OrderMenuRepository orderMenuRepository;

    public String cancel(Long userId, Long orderMenuId) {
        OrderMenu orderMenu = orderMenuRepository.validateOrderMenuForUserIdAndId(userId, orderMenuId);

        PaymentService paymentService = paymentServiceMap.get(orderMenu.getPayment());

        orderMenuResultUpdateService.update(orderMenuId, "CANCELED");

        return paymentService.refund(userId, orderMenu);
    }
}