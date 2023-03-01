package com.example.foodcloud.domain.order.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderMenu, Long> {
}
