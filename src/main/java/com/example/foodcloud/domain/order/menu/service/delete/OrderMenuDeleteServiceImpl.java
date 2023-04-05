package com.example.foodcloud.domain.order.menu.service.delete;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuDeleteServiceImpl implements OrderMenuDeleteService {
    private final OrderMenuRepository orderMenuRepository;

    /**
     * OrderMenu를 삭제하는 메소드
     * userId와 orderMenuId를 받아와서 삭제함
     */
    @Override
    public void delete(Long userId, Long orderMenuId) {
        orderMenuRepository.findByUserIdAndId(userId, orderMenuId)
                .ifPresent(orderMenuRepository::delete);
    }
}