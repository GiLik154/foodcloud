package com.example.foodcloud.entity;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderMenu, Long> {
}
