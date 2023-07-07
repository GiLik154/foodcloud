package com.example.foodcloud.domain.groupbuylist.service.update;

import com.example.foodcloud.domain.groupbuylist.service.GroupBuyListResultUpdater;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupResultUpdateServiceImpl implements OrderJoinGroupResultUpdateService {
    private final GroupBuyListRepository groupBuyListRepository;
    private final GroupBuyListResultUpdater groupBuyListResultUpdater;

    @Override
    public void update(Long userId, Long orderJoinGroupId, String result) {
        Optional<GroupBuyList> orderJoinGroupOptional = groupBuyListRepository.findByUserIdAndId(userId, orderJoinGroupId);

        orderJoinGroupOptional.ifPresent(orderJoinGroup -> {
            OrderResult orderResult = OrderResult.valueOf(result);
            orderJoinGroup.updateResult(orderResult);

            groupBuyListResultUpdater.update(orderJoinGroupId, orderResult);
        });
    }
}
