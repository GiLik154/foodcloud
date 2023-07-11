package com.example.foodcloud.domain.groupbuylist.service;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.groupbuylist.service.commend.OrderJoinGroupCreatorCommend;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupByListCreatorTest {
    private final GroupByListCreator groupByListCreator;
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public GroupByListCreatorTest(GroupByListCreator GroupByListCreator,
                                  GroupBuyListRepository GroupBuyListRepository,
                                  UserRepository userRepository,
                                  RestaurantRepository restaurantRepository) {

        this.groupByListCreator = GroupByListCreator;
        this.groupBuyListRepository = GroupBuyListRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 오더_메뉴_추가_정삭상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        OrderJoinGroupCreatorCommend OrderJoinGroupCreatorCommend = new OrderJoinGroupCreatorCommend("testLocation", restaurantId);
        groupByListCreator.craete(userId, OrderJoinGroupCreatorCommend);

        GroupBuyList groupBuyList = groupBuyListRepository.findByUserId(userId).get(0);

        assertEquals("testLocation", groupBuyList.getLocation());
        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
        assertEquals(user, groupBuyList.getUser());
        assertEquals(restaurant, groupBuyList.getRestaurant());
        assertNotNull(groupBuyList.getTime());
    }

    @Test
    void 오더_메뉴_추가_유저고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        OrderJoinGroupCreatorCommend OrderJoinGroupCreatorCommend = new OrderJoinGroupCreatorCommend("testLocation", restaurantId);

        assertThrows(UsernameNotFoundException.class, () -> groupByListCreator.craete(userId + 1L, OrderJoinGroupCreatorCommend));
    }

    @Test
    void 오더_메뉴_추가_식당고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        OrderJoinGroupCreatorCommend OrderJoinGroupCreatorCommend = new OrderJoinGroupCreatorCommend("testLocation", restaurantId + 1L);

        assertThrows(NotFoundRestaurantException.class, () -> groupByListCreator.craete(userId, OrderJoinGroupCreatorCommend));
    }
}