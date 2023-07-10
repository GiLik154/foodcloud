package com.example.foodcloud.application.order;

import com.example.foodcloud.application.order.commend.NewOrderServiceCommend;

public interface NewOrder {
    /**
     * 유저의 ID와 DTO로 orderJoinGroup를 생성한다.
     * 이후 유저의 ID와 DTO의 정보로 OrderMenu를 생성한다.
     *
     * @param userId             유저의 ID
     * @param newOrderServiceCommend 새로운 주문의 정보가 담긴 DTO
     */
    Long order(Long userId, NewOrderServiceCommend newOrderServiceCommend);
}
