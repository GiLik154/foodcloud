package com.example.foodcloud.domain.ordermenu.join.service.update;

import com.example.foodcloud.GroupBuyListFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.groupbuylist.service.update.OrderJoinGroupResultUpdateService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupBuyListResultUpdateServiceImplTest {
    private final OrderJoinGroupResultUpdateService orderJoinGroupResultUpdateService;
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public GroupBuyListResultUpdateServiceImplTest(OrderJoinGroupResultUpdateService OrderJoinGroupResultUpdateService,
                                                   GroupBuyListRepository GroupBuyListRepository,
                                                   UserRepository userRepository,
                                                   RestaurantRepository restaurantRepository) {
        this.orderJoinGroupResultUpdateService = OrderJoinGroupResultUpdateService;
        this.groupBuyListRepository = GroupBuyListRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 메인주문_업데이트_정상_작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long groupBuyListId = groupBuyList.getId();

        orderJoinGroupResultUpdateService.update(userId, groupBuyListId, "PREPARED");

        assertEquals(OrderResult.PREPARED, groupBuyList.getResult());
    }

    @Test
    void 메인주문_업데이트_유저고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long groupBuyListId = groupBuyList.getId();

        orderJoinGroupResultUpdateService.update(userId + 1L, groupBuyListId, "PREPARED");

        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
    }

    @Test
    void 메인주문_업데이트_오더고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long groupBuyListId = groupBuyList.getId();

        orderJoinGroupResultUpdateService.update(userId, groupBuyListId, "PREPARED");

        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
    }
}

//todo 엔티티 수정하면서 깨지는 부분 모두 수정해야함