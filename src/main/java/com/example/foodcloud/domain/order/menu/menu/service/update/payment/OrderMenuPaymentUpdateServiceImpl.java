package com.example.foodcloud.domain.order.menu.menu.service.update.payment;

import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service()
@Transactional
@RequiredArgsConstructor
public class OrderMenuPaymentUpdateServiceImpl implements OrderMenuPaymentUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    public <T extends Payment> boolean update(Long orderMenuId, T payment) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);
        if (orderMenuOptional.isPresent()) {
            OrderMenu orderMenu = orderMenuOptional.get();

            payment.orderMenuUpdate(orderMenu);
            return true;

        }
        return false;
    }
}
