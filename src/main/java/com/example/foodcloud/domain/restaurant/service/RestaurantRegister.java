package com.example.foodcloud.domain.restaurant.service;

import com.example.foodcloud.domain.restaurant.service.commend.RestaurantRegisterCommend;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface RestaurantRegister {
    /**
     * 유저의 ID로 유저가 존재하는지 확인한다.
     * 존재하면 {@link RestaurantRegisterCommend}를 통해 식당을 생성한다.
     *
     * @param userId                    유저의 아이디
     * @param restaurantRegisterCommend 식당의 생성 정보
     * @throws UsernameNotFoundException 유저의 ID로 유저가 존재하는지
     *                                   확인할 때 존재하지 않으면 발생
     */
    void register(Long userId, RestaurantRegisterCommend restaurantRegisterCommend);
}
