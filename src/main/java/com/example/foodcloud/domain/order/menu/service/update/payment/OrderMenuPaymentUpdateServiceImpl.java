package com.example.foodcloud.domain.order.menu.service.update.payment;

import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.point.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service()
@Transactional
@RequiredArgsConstructor
public class OrderMenuPaymentUpdateServiceImpl implements OrderMenuPaymentUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    public void update(Long orderMenuId, Payment payment) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        orderMenuOptional.ifPresent(orderMenu ->
                orderMenu.updatePayment(payment));
    }
}
