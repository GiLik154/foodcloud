package com.example.foodcloud.domain.foodmenu.service;

public interface FoodMenuCountIncrease {
    /**
     * 음식 메뉴의 ID로 음식 메뉴가 존재하는지 확인한다.
     * 존재하면 주어진 음식 메뉴의 주문 수량을 1 증가시킨다.
     * 동시에 여러 사용자가 접근하여 데이터 불일치 문제가 발생하지 않도록,
     * 비관적 락을 사용하여 해당 음식 메뉴 레코드를 업데이트한다.
     *
     * @param foodMenuId 증가시킬 음식 메뉴의 ID
     */
    void increase(Long foodMenuId);
}