package com.example.foodcloud.domain.restaurant.service;

import org.springframework.security.authentication.BadCredentialsException;

public interface RestaurantDeleter {
    /**
     * 유저의 ID를 사용해서 패스워드를 비교한다.
     * 패스워드가 일치하면 식당의 ID로 식당이 존재하는지 확인한다.
     * 존재하면 해당 식당을 삭제한다.
     *
     * @param userId       유저의 아이디
     * @param password     유저가 입력한 패스워드
     * @param restaurantId 식당의 아이디
     * @throws BadCredentialsException 유저의 ID와 패스워드가 다르면
     *                                 BadCredentialsException익셉션이 발생한다.
     */
    void delete(Long userId, String password, Long restaurantId);
}
