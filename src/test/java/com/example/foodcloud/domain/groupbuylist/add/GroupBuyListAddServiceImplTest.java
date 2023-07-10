package com.example.foodcloud.domain.groupbuylist.add;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.groupbuylist.service.OrderJoinGroupCreator;
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
class GroupBuyListAddServiceImplTest {
    private final OrderJoinGroupCreator orderJoinGroupCreator;
    private final GroupBuyListRepository groupBuyListRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public GroupBuyListAddServiceImplTest(OrderJoinGroupCreator OrderJoinGroupCreator,
                                          GroupBuyListRepository GroupBuyListRepository,
                                          UserRepository userRepository,
                                          RestaurantRepository restaurantRepository) {

        this.orderJoinGroupCreator = OrderJoinGroupCreator;
        this.groupBuyListRepository = GroupBuyListRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 오더_메뉴_추가_정삭상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        OrderJoinGroupCreatorCommend OrderJoinGroupCreatorCommend = new OrderJoinGroupCreatorCommend("test", restaurantId);
        orderJoinGroupCreator.add(userId, OrderJoinGroupCreatorCommend);
        GroupBuyList groupBuyList = groupBuyListRepository.findByUserId(userId).get(0);

        assertEquals("test", groupBuyList.getLocation());
        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
        assertEquals(user, groupBuyList.getUser());
        assertEquals(restaurant, groupBuyList.getRestaurant());
        assertNotNull(groupBuyList.getTime());
    }

    @Test
    void 오더_메뉴_추가_유저고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderJoinGroupCreatorCommend OrderJoinGroupCreatorCommend = new OrderJoinGroupCreatorCommend("test", restaurantId);

        assertThrows(UsernameNotFoundException.class, () ->
                orderJoinGroupCreator.add(userId + 1L, OrderJoinGroupCreatorCommend)
        );
    }

    @Test
    void 오더_메뉴_추가_식당고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        String a = "a";
        System.out.println(a.hashCode() + "ㅁㄴㅇㄹ");

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderJoinGroupCreatorCommend OrderJoinGroupCreatorCommend = new OrderJoinGroupCreatorCommend("test", restaurantId + 1L);

        NotFoundRestaurantException e = assertThrows(NotFoundRestaurantException.class, () ->
                orderJoinGroupCreator.add(userId, OrderJoinGroupCreatorCommend)
        );
        assertEquals("Not found Restaurant", e.getMessage());
    }
}