package com.example.foodcloud.entity;


import com.example.foodcloud.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    boolean existsById(Long id);
}
