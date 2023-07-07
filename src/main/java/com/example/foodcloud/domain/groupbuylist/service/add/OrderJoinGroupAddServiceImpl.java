package com.example.foodcloud.domain.groupbuylist.service.add;

import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.groupbuylist.service.add.dto.OrderJoinGroupAddServiceDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupAddServiceImpl implements OrderJoinGroupAddService {
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Long add(Long userId, OrderJoinGroupAddServiceDto orderJoinGroupAddServiceDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(orderJoinGroupAddServiceDto.getRestaurantId()).orElseThrow(() -> new NotFoundRestaurantException("Not found Restaurant"));

        GroupBuyList groupBuyList = new GroupBuyList(orderJoinGroupAddServiceDto.getLocation(),
                user,
                restaurant);

        groupBuyListRepository.save(groupBuyList);

        return groupBuyList.getId();
    }
}
