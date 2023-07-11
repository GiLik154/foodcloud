package com.example.foodcloud.domain.groupbuylist.domain;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupBuyListTest {
    @Test
    void 최상의_생성_테스트() {
        User user = UserFixture.fixture().build();
        Restaurant restaurant = RestaurantFixture.fixture(user).build();

        GroupBuyList groupBuyList = new GroupBuyList("testLocation", user, restaurant);

        assertEquals("testLocation", groupBuyList.getLocation());
        assertEquals(user, groupBuyList.getUser());
        assertEquals(restaurant, groupBuyList.getRestaurant());
        assertEquals(OrderResult.PAYMENT_WAITING, groupBuyList.getResult());
    }

    @Test
    void 주문_결과_업데이트() {
        User user = UserFixture.fixture().build();
        Restaurant restaurant = RestaurantFixture.fixture(user).build();

        GroupBuyList groupBuyList = new GroupBuyList("testLocation", user, restaurant);
        groupBuyList.updateResult(OrderResult.COOKING);

        assertEquals(OrderResult.COOKING, groupBuyList.getResult());
    }
}