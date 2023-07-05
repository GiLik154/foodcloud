package com.example.foodcloud.domain.groupbuylist.service.add;

import com.example.foodcloud.domain.groupbuylist.service.add.dto.NewOrderServiceDto;
import com.example.foodcloud.domain.ordermenu.service.add.OrderMenuAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class NewOrderServiceImpl implements NewOrderService {
    private final OrderJoinGroupAddService orderJoinGroupAddService;
    private final OrderMenuAddService orderMenuAddService;

    @Override
    public Long order(Long userId, NewOrderServiceDto newOrderServiceDto) {
        Long orderJoinGroupId = orderJoinGroupAddService.add(userId, newOrderServiceDto.convertOrderJoinGroupDto());

        return orderMenuAddService.add(userId, newOrderServiceDto.convertOrderMenuDto(orderJoinGroupId));
    }
}
