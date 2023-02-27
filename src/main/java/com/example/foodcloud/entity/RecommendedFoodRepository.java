package com.example.foodcloud.entity;


import com.example.foodcloud.entity.RecommendedFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedFoodRepository extends JpaRepository<RecommendedFood, Long> {
}
