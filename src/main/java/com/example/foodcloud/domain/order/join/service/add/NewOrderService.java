package com.example.foodcloud.domain.order.join.service.add;

import com.example.foodcloud.domain.order.join.service.add.dto.NewOrderServiceDto;

public interface NewOrderService {
    //todo 구글독 작성해야함
    Long order(Long userId, NewOrderServiceDto newOrderServiceDto);
}
