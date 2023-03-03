package com.example.foodcloud.domain.ordermain.service.delete;

import com.example.foodcloud.domain.ordermain.domain.OrderMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMainDeleteServiceImpl implements OrderMainDeleteService {
    private final OrderMainRepository orderMainRepository;

    @Override
    public boolean delete(Long userId, Long orderMainId) {
        if (orderMainRepository.existsByUserIdAndId(userId, orderMainId)) {

            orderMainRepository.deleteById(orderMainId);
            return true;
        }
        return false;
    }
}
