package com.example.foodcloud.domain.foodmenu.service;

import org.springframework.security.authentication.BadCredentialsException;

public interface FoodMenuDeleter {
    /**
     * 유저의 ID와 음식 메뉴의 ID로 해당 음식이 존재하는지 확인
     * 존재한다면 유저의 ID와 패스워드를 일치하는지 확인.
     * 유저의 ID와 패스워드가 일치하면 음식 메뉴를 삭제함
     *
     * @param userId     사용자의 ID
     * @param foodMenuId 음식 메뉴의 ID
     * @param password   사용자의 패스워드
     * @throws BadCredentialsException 유저의 ID와 패스워드가 일치하지 않을 경우 발생.
     */
    void delete(Long userId, Long foodMenuId, String password);
}
