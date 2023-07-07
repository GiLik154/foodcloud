package com.example.foodcloud.domain.groupbuylist.service;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupBuyListResultUpdaterImpl implements GroupBuyListResultUpdater {
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public void update(Long orderJoinGroupId, OrderResult orderResult) {
        List<OrderMenu> list = orderMenuRepository.findByGroupBuyListId(orderJoinGroupId);

        list.forEach(orderMenu ->
                orderMenu.updateResult(orderResult));
    }
}
