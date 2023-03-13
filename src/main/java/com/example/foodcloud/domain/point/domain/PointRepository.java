package com.example.foodcloud.domain.point.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    boolean existsByUserId(Long userId);
    Optional<Point> findByUserId(Long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM Point p WHERE p.user.id = :userId ORDER BY p.id DESC")
    Point findByUserIdOrderByIdDescForUpdate(Long userId);
}
