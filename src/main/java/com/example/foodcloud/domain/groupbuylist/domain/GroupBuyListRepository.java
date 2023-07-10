package com.example.foodcloud.domain.groupbuylist.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupBuyListRepository extends JpaRepository<GroupBuyList, Long> {
    List<GroupBuyList> findByUserId(Long userId);

    Optional<GroupBuyList> findByUserIdAndId(Long userId, Long orderJoinGroupId);
}
