package com.example.foodcloud.domain.order.menu.menu.service.cancel;

import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItems;
import com.example.foodcloud.domain.order.menu.item.domain.OrderMenuItemsRepository;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.payment.domain.OrderMenuPayment;
import com.example.foodcloud.domain.order.menu.payment.domain.OrderMenuPaymentRepository;
import com.example.foodcloud.domain.payment.bank.service.payment.PaymentService;
import com.example.foodcloud.domain.order.menu.menu.service.update.OrderMenuResultUpdateService;
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
    private final OrderMenuItemsRepository orderMenuItemsRepository;
    private final OrderMenuPaymentRepository orderMenuPaymentRepository;

    public String cancel(Long userId, Long orderMenuId) {
        OrderMenuItems orderMenuItems = orderMenuItemsRepository.validate(orderMenuId);
        OrderMenuPayment orderMenuPayment = orderMenuPaymentRepository.validate(orderMenuId);

        PaymentService paymentService = paymentServiceMap.get(orderMenuPayment.getPayment());

        orderMenuResultUpdateService.update(orderMenuId, "CANCELED");

        return paymentService.refund(userId, orderMenuItems);
    }
}
