package com.example.foodcloud.domain.recommend.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendFoodRepository extends JpaRepository<RecommendFood, Long> {
}
