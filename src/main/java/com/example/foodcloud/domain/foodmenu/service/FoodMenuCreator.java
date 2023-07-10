package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuCreatorCommend;

import java.io.File;

public interface FoodMenuCreator {
    /**
     * 식당의 id와 사용자의 id를 사용해 해당 식당이 존재하는지 확인한다.
     * 존재한다면 FoodMenuAddServiceDto로부터 받은 정보로 FoodMenu 객체를 생성하고,
     * 이미지 파일이 존재한다면 파일을 저장하고, FoodMenu를 DB에 저장한다.
     *
     * @param userId                사용자 id
     * @param restaurantId          식당 id
     * @param foodMenuCreatorCommend 추가할 음식 메뉴 정보
     * @param file                  이미지 파일
     */
    void create(Long userId, Long restaurantId, FoodMenuCreatorCommend foodMenuCreatorCommend, File file);
}
