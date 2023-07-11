package com.example.foodcloud.domain.groupbuylist.service;

import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.groupbuylist.service.commend.OrderJoinGroupCreatorCommend;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundGroupByListException;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupBuyListService implements GroupByListCreator, GroupByListUpdater, GroupByListDeleter {
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public Long craete(Long userId, OrderJoinGroupCreatorCommend commend) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(commend.getRestaurantId()).orElseThrow(() -> new NotFoundRestaurantException("Not found Restaurant"));

        GroupBuyList groupBuyList = new GroupBuyList(commend.getLocation(), user, restaurant);

        groupBuyListRepository.save(groupBuyList);

        return groupBuyList.getId();
    }

    @Override
    public void update(Long userId, Long orderJoinGroupId, OrderResult orderResult) {
        GroupBuyList groupBuyList = groupBuyListRepository.findByUserIdAndId(userId, orderJoinGroupId).orElseThrow(() ->
                new NotFoundGroupByListException("Not found GroupByList"));

        groupBuyList.updateResult(orderResult);

        updateForOrderMenu(orderJoinGroupId, orderResult);
    }

    private void updateForOrderMenu(Long orderJoinGroupId, OrderResult orderResult) {
        List<OrderMenu> list = orderMenuRepository.findByGroupBuyListId(orderJoinGroupId);

        list.forEach(orderMenu ->
                orderMenu.updateResult(orderResult));
    }

    @Override
    public void delete(Long userId, Long orderJoinGroupId) {
        Optional<GroupBuyList> orderJoinGroupOptional = groupBuyListRepository.findByUserIdAndId(userId, orderJoinGroupId);

        orderJoinGroupOptional.ifPresent(groupBuyListRepository::delete);
    }
}
