package com.example.foodcloud.domain.order.menu.service.update;

import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuResultUpdateServiceImpl implements OrderMenuResultUpdateService {

    private final OrderMenuRepository orderMenuRepository;

    @Override
    public void update(Long orderMenuId, String result) {
        Optional<OrderMenu> orderMenuOptional = orderMenuRepository.findById(orderMenuId);

        orderMenuOptional.ifPresent(orderMenu ->
                orderMenu.updateResult(OrderResult.valueOf(result)));
    }
}
