package com.example.foodcloud.domain.ordermenu.service.cancel;

import com.example.foodcloud.domain.ordermenu.service.update.OrderMenuResultUpdateService;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
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
        OrderMenu orderMenu = orderMenuRepository.getValidByUserIdAndId(userId, orderMenuId);

        orderMenuResultUpdateService.update(orderMenuId, "CANCELED");

        PaymentService paymentService = paymentServiceMap.get(orderMenu.getPayment().getPaymentCode().getCode());

        return paymentService.refund(userId, orderMenu);
    }
}