package com.example.foodcloud.domain.groupbuylist.service;

import com.example.foodcloud.enums.OrderResult;

public interface GroupBuyListResultUpdater {
    /**
     * orderJoinGroup의 ID로 관련된 OrderMenu를 전부 찾아온다.
     * 찾아온 OrderMenu의 결과를 매개변수로 받은 결과로 모든 OrderMenu를 수정한다.
     *
     * @param orderJoinGroupId orderMenuId의 ID
     * @param orderResult      수정할 결과
     */
    void update(Long orderJoinGroupId, OrderResult orderResult);
}
