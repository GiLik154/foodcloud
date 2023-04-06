package com.example.foodcloud.domain.order.join.service.delete;

public interface OrderJoinGroupDeleteService {
    /**
     * 유저의 ID와 OrderJoinGroup의 ID로 OrderJoinGroup가 존재하는지 확인한다.
     * 존재하면 해당 OrderJoinGroup를 삭제한다
     *
     * @param userId           유저의 ID
     * @param orderJoinGroupId OrderJoinGroup의 ID
     */
    void delete(Long userId, Long orderJoinGroupId);
}
