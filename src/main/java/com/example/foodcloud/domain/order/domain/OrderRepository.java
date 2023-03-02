package com.example.foodcloud.domain.order.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByUserID(Long userId);
}
