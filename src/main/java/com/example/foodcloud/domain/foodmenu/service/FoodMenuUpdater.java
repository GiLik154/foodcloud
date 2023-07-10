package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuUpdaterCommend;

import java.io.File;

public interface FoodMenuUpdater {
    /**
     * 음식 메뉴의 id를 사용해 해당 음식 메뉴가 존재하는지 확인한다.
     * 음식메뉴가 존재하고, 이미지 파일이 존재한다면 파일을 저장하고, FoodMenu를 DB에 저장한다.
     * foodMenuUpdateServiceDto로부터 받은 정보로 FoodMenu를 업데이트 한다.
     *
     * @param foodMenuId               식당 id
     * @param foodMenuUpdaterCommend 추가할 음식 메뉴 정보
     * @param file                     이미지 파일
     */
    void update(Long foodMenuId, FoodMenuUpdaterCommend foodMenuUpdaterCommend, File file);

    /**
     * 음식 메뉴의 ID로 음식 메뉴가 존재하는지 확인한다.
     * 존재하면 주어진 음식 메뉴의 주문 수량을 1 증가시킨다.
     * 동시에 여러 사용자가 접근하여 데이터 불일치 문제가 발생하지 않도록,
     * 비관적 락을 사용하여 해당 음식 메뉴 레코드를 업데이트한다.
     *
     * @param foodMenuId 증가시킬 음식 메뉴의 ID
     */
    void increaseOrderCount(Long foodMenuId);
}

