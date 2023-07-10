package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuRegisterCommend;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.exception.NotFoundGroupByListException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface OrderMenuRegister {
    /**
     * 유저의 ID를 통해 유저가 존재하는지 확인
     * 음식 메뉴의 ID를 통해 음식 메뉴가 존재하는지 확인.
     * 유저의 ID와 OrderJoinGroup의 ID를 통해 결과가 'CANCELED'인 OrderJoinGroup가 존재하는지 확인
     * 모두 존재하면 유저와 음식메뉴와 OrderJoinGroup과 현재 시간, DTO로 OrderMenu객체를 생성한다.
     * 생성이 완료되면 식당과 음식의 주문 횟수를 1씩 증가시킴
     *
     * @param userId                 유저의 아이디
     * @param orderMenuRegisterCommend add하는 정보가 들어있는 dto
     * @throws UsernameNotFoundException       유저의 ID를 통해 유저가 존재하는지 확인 할 때
     *                                         존재하지 않으면 발생
     * @throws NotFoundFoodMenuException       음식 메뉴의 ID를 통해 음식 메뉴가 존재하는지 확인
     *                                         할 때 존재하지 않으면 발생
     * @throws NotFoundGroupByListException 유저의 ID와 OrderJoinGroup의 ID를 통해 결과가
     *                                         'CANCELED'인 OrderJoinGroup가 존재하는지 확인할 때
     *                                         존재하지 않으면 발생
     */
    Long register(Long userId, OrderMenuRegisterCommend orderMenuRegisterCommend);
}