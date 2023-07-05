package com.example.foodcloud.domain.ordermenu.service.delete;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuDeleteServiceImpl implements OrderMenuDeleteService {
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public void delete(Long userId, Long orderMenuId) {
        orderMenuRepository.findByUserIdAndId(userId, orderMenuId)
                .ifPresent(orderMenuRepository::delete);
    }
}