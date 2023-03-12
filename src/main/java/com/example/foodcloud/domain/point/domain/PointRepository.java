package com.example.foodcloud.domain.point.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface PointRepository extends JpaRepository<Point, Long> {
    boolean existsByUserId(Long userId);

    Point findByUserIdOrderByIdDesc(Long userId);

    //todo 비관적 락 추가해야함 (@Lock(LockModeType.OPTIMISTIC))
}
