package com.example.foodcloud.domain.groupbuylist.service;

import com.example.foodcloud.domain.groupbuylist.service.commend.OrderJoinGroupCreatorCommend;

public interface GroupByListCreator {
    /**
     * 유저의 ID로 유저가 존재하는지 확인한다.
     * DTO의 식당의 ID로 식당이 존재하는지 확인한다.
     * 두 개가 모두 존재하면 OrderJoinGroup의 객체를 만들어 JPA로 save한다.
     * 이후 OrderJoinGroup의 ID를 반환한다.
     *
     * @param userId                      유저의 ID
     * @param orderJoinGroupCreatorCommend OrderJoinGroup를 생성하기 위한
     *                                    정보가 담긴 DTO
     * @return OrderJoinGroup의 ID
     */
    Long craete(Long userId, OrderJoinGroupCreatorCommend orderJoinGroupCreatorCommend);
}