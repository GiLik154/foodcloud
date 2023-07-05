package com.example.foodcloud.domain.groupbuylist.service.delete;

import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupDeleteServiceImpl implements OrderJoinGroupDeleteService {
    private final GroupBuyListRepository groupBuyListRepository;

    @Override
    public void delete(Long userId, Long orderJoinGroupId) {
        Optional<GroupBuyList> orderJoinGroupOptional = groupBuyListRepository.findByUserIdAndId(userId, orderJoinGroupId);

        orderJoinGroupOptional.ifPresent(groupBuyListRepository::delete);
    }
}
