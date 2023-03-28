package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.order.main.service.add.dto.NewOrderServiceDto;
import com.example.foodcloud.domain.order.menu.menu.service.add.OrderMenuAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class NewOrderServiceImpl implements NewOrderService {
    private final OrderMainAddService orderMainAddService;
    private final OrderMenuAddService orderMenuAddService;

    @Override
    public void order(Long userId, NewOrderServiceDto newOrderServiceDto) {
        Long orderMainId = orderMainAddService.add(userId, newOrderServiceDto.convertMainDto());

        orderMenuAddService.add(userId, newOrderServiceDto.convertMenuDto(orderMainId));
    }
}
