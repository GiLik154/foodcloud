package com.example.foodcloud.domain.order.join.service.add;

import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.join.service.add.dto.OrderJoinGroupAddServiceDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupAddServiceImpl implements OrderJoinGroupAddService {
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Long add(Long userId, OrderJoinGroupAddServiceDto orderJoinGroupAddServiceDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Restaurant restaurant = restaurantRepository.getValidById(orderJoinGroupAddServiceDto.getRestaurantId());

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup(orderJoinGroupAddServiceDto.getLocation(),
                getTime(),
                user,
                restaurant);

        orderJoinGroupRepository.save(orderJoinGroup);

        return orderJoinGroup.getId();
    }

    private String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
