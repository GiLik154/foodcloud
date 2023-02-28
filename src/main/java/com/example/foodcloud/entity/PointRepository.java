package com.example.foodcloud.entity;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    boolean existsById(Long userId);
    Point findByUserIdOrderByIdDesc(Long userId);
}
