package com.example.foodcloud.domain.order.join.service.add;

import com.example.foodcloud.domain.order.join.service.add.dto.NewOrderServiceDto;

public interface NewOrderService {
    /**
     * 유저의 ID와 DTO로 orderJoinGroup를 생성한다.
     * 이후 유저의 ID와 DTO의 정보로 OrderMenu를 생성한다.
     *
     * @param userId             유저의 ID
     * @param newOrderServiceDto 새로운 주문의 정보가 담긴 DTO
     */
    Long order(Long userId, NewOrderServiceDto newOrderServiceDto);
}
