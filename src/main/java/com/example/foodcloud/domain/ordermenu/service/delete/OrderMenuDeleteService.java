package com.example.foodcloud.domain.ordermenu.service.delete;

public interface OrderMenuDeleteService {

    /**
     * 유저의 ID와 오더메인의 ID로 오더메인이 존재하는지 확인
     * 존재하면 오더 메인을 삭제함.
     *
     * @param userId      유저의 ID
     * @param orderMenuId 오더메인의 ID
     */
    void delete(Long userId, Long orderMenuId);
}
