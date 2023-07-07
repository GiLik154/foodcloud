package com.example.foodcloud.domain.groupbuylist.delete;

import com.example.foodcloud.GroupBuyListFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.groupbuylist.service.delete.OrderJoinGroupDeleteService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupBuyListDeleteServiceImplTest {
    private final OrderJoinGroupDeleteService orderJoinGroupDeleteService;
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public GroupBuyListDeleteServiceImplTest(OrderJoinGroupDeleteService orderJoinGroupDeleteService,
                                             GroupBuyListRepository groupBuyListRepository,
                                             UserRepository userRepository,
                                             RestaurantRepository restaurantRepository) {
        this.orderJoinGroupDeleteService = orderJoinGroupDeleteService;
        this.groupBuyListRepository = groupBuyListRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 오더메인_삭제_정상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();


        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        orderJoinGroupDeleteService.delete(userId, groupBuyList.getId());

        assertFalse(groupBuyListRepository.existsById(groupBuyList.getId()));
    }

    @Test
    void 오더메인_삭제_유저_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        orderJoinGroupDeleteService.delete(userId + 1L, groupBuyList.getId());

        assertTrue(groupBuyListRepository.existsById(groupBuyList.getId()));
    }

    @Test
    void 오더메인_삭제_오더메인_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        orderJoinGroupDeleteService.delete(userId, groupBuyList.getId() + 1L);

        assertTrue(groupBuyListRepository.existsById(groupBuyList.getId()));
    }
}