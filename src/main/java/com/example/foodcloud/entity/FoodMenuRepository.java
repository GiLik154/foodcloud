package com.example.foodcloud.entity;


import com.example.foodcloud.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
}
