package com.example.foodcloud.domain.order.menu.service.update;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuListResultUpdateServiceImpl implements OrderMenuListResultUpdateService {
    private final OrderMenuRepository orderMenuRepository;

    /**
     * OrderMenu List의 Result를 모두 update 하는 메소드
     * OrderJoinGroup과 관련있는 모든 list를 받아 온 후에
     * OrderMenu의 result를 수정함
     */
    @Override
    public void update(Long orderJoinGroupId, OrderResult orderResult) {
        List<OrderMenu> list = orderMenuRepository.findByOrderJoinGroupId(orderJoinGroupId);

        list.forEach(orderMenu ->
                orderMenu.updateResult(orderResult));
    }
}
