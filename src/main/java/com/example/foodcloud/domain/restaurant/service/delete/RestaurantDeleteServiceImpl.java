package com.example.foodcloud.domain.restaurant.service.delete;


import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantDeleteServiceImpl implements RestaurantDeleteService {
    private final RestaurantRepository restaurantRepository;
    private final ValidateUserPasswordService validateUserPasswordService;


    /**
     * 식당을 삭제하기 위해서
     * 1. 유저를 검증 (유저의 고유번호와 패스워드)
     *   -> 검증에 실패 시 익셉션 발생
     * 2. 이후 식당의 고유번호로 식당을 찾아서 삭제함
     */
    @Override
    public void delete(Long userId, Long restaurantId, String password) {
        validateUserPasswordService.validate(userId, password);

        restaurantRepository.findById(restaurantId).
                ifPresent(restaurantRepository::delete);
    }
}