package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddServiceDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface RestaurantRegister {
    /**
     * 유저의 ID로 유저가 존재하는지 확인한다.
     * 존재하면 DTO와 유저를 통해 Restaurant 갹체를 생성한다.
     * JPA의 save를 이용해서 DB에 Insert한다.
     *
     * @param userId                  유저의 아이디
     * @param restaurantAddServiceDto 식당의 추가 정보 DTO
     * @throws UsernameNotFoundException 유저의 ID로 유저가 존재하는지
     *                                   확인할 때 존재하지 않으면 발생
     */
    void add(Long userId, RestaurantAddServiceDto restaurantAddServiceDto);
}
