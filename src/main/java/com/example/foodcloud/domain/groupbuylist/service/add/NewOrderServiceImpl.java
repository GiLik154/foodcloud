package com.example.foodcloud.domain.groupbuylist.service.add;

import com.example.foodcloud.domain.groupbuylist.service.add.dto.NewOrderServiceDto;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class NewOrderServiceImpl implements NewOrderService {
    private final OrderJoinGroupAddService orderJoinGroupAddService;
    private final OrderMenuRegister orderMenuRegister;

    @Override
    public Long order(Long userId, NewOrderServiceDto newOrderServiceDto) {
        Long orderJoinGroupId = orderJoinGroupAddService.add(userId, newOrderServiceDto.convertOrderJoinGroupDto());

        return orderMenuRegister.register(userId, newOrderServiceDto.convertOrderMenuDto(orderJoinGroupId));
    }
}
