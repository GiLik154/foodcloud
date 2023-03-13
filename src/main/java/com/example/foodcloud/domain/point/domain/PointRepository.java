package com.example.foodcloud.domain.point.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    boolean existsByUserId(Long userId);

    Point findByUserIdOrderByIdDesc(Long userId);

    //todo 비관적 락 추가해야함 (@Lock(LockModeType.OPTIMISTIC))
}
