package com.example.foodcloud.domain.restaurant.service.update.dto;

import lombok.Getter;

@Getter
public class RestaurantUpdateServiceDto {
    /** 식당의 이름 */
    private final String name;
    /** 식당의 장소 */
    private final String location;
    /** 식당의 운영 시간 */
    private final String businessHours;

    public RestaurantUpdateServiceDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }
}
