package com.example.foodcloud.domain.groupbuylist.service.update;

public interface OrderJoinGroupResultUpdateService {
    /**
     * 유저의 ID와 OrderJoinGroup의 ID로 OrderJoinGroup가 존재하는지 확인한다.
     * 존재하면 result로 OrderResult를 찾고
     * OrderJoinGroup의 result를 변경한다.
     * 우히 OrderJoinGroup와 관련있는 OrderMenu의 모든 result도 변경한다.
     *
     * @param userId           유저의 ID
     * @param orderJoinGroupId OrderJoinGroup의 ID
     * @param result           수정할 결과값
     */
    void update(Long userId, Long orderJoinGroupId, String result);
}
