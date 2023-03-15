package com.example.foodcloud.domain.order.menu.service.cancel;

import com.example.foodcloud.domain.bank.service.payment.PaymentService;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.update.OrderMenuResultUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuCancelServiceImpl implements OrderMenuCancelService {
    private final OrderMenuResultUpdateService orderMenuResultUpdateService;
    private final Map<String, PaymentService> paymentServiceMap;
    private final OrderMenuRepository orderMenuRepository;

    public boolean isCancel(Long userId, Long orderMenuId) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findByUserIdAndId(userId, orderMenuId);
        if (orderMenuOptional.isPresent()) {

            OrderMenu orderMenu = orderMenuOptional.get();

            PaymentService paymentService = paymentServiceMap.get(orderMenu.getBankAccount().getBank());


        }
        return false;
    }
}
