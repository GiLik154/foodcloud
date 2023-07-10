package com.example.foodcloud.application.image;

import java.io.File;

public interface ImageUploader {
    /**
     * 식당의 이름을 String으로 받는다.
     * creatFileName메소드를 통해 파일 이름을 만든다.
     * 이름은 업로드 한 시간이며, 중복을 피하기 위해 나노초 까지 사용한다
     * "food-menu-images/" + restaurantName + "/" 의 경로에 폴더가 있는지 확인하고, 없을 시 생성한다.
     * 이미지를 업로드하고, 업로드 한 Path를 반환한다
     *
     * @param restaurantName 음식이 등록되는 식당 이름
     * @param file           파일
     * @return 이미지가 업로드 된 path 주소
     */
    String savedFileAndReturnFilePath(String restaurantName, File file);
}
