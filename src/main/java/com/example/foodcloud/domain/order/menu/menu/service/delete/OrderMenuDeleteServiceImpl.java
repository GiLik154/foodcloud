package com.example.foodcloud.domain.order.menu.menu.service.delete;

import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuDeleteServiceImpl implements OrderMenuDeleteService {
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public boolean delete(Long userId, Long orderMenuId) {
        if (orderMenuRepository.existsByUserIdAndId(userId, orderMenuId)) {

            orderMenuRepository.deleteById(orderMenuId);

            return true;
        }
        return false;
    }
}