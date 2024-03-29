package com.example.foodcloud.domain.groupbuylist.service;

import com.example.foodcloud.GroupBuyListFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundGroupByListException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupByListUpdaterTest {
    private final GroupByListUpdater groupByListUpdater;
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public GroupByListUpdaterTest(GroupByListUpdater GroupByListUpdater,
                                  GroupBuyListRepository GroupBuyListRepository,
                                  UserRepository userRepository,
                                  RestaurantRepository restaurantRepository) {
        this.groupByListUpdater = GroupByListUpdater;
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

        groupByListUpdater.update(userId, groupBuyListId, OrderResult.PREPARED);

        assertEquals(OrderResult.PREPARED, groupBuyList.getResult());
    }

    @Test
    void 유저의_고유변호가_다르면_익셉션_발생() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long groupBuyListId = groupBuyList.getId();

        assertThrows(NotFoundGroupByListException.class, () -> groupByListUpdater.update(userId + 1L, groupBuyListId, OrderResult.PREPARED));

        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
    }

    @Test
    void 공구리스트의_고유번호가_다르면_익셉션_발생() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long groupBuyListId = groupBuyList.getId();

        assertThrows(NotFoundGroupByListException.class, () -> groupByListUpdater.update(userId, groupBuyListId + 1L, OrderResult.PREPARED));

        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
    }
}