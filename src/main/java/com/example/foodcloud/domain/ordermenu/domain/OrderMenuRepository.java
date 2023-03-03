package com.example.foodcloud.domain.ordermenu.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByOrderMainId(Long orderMainId);
}
