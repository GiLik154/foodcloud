package com.example.foodcloud.domain.order.menu.item.domain;

import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderMenuItemsRepository extends JpaRepository<OrderMenuItems, Long> {

    default OrderMenuItems validate(Long orderMenuId) {
        return findById(orderMenuId).orElseThrow(NotFoundFoodMenuException::new);
    }
}
