package com.example.foodcloud.domain.ordermenu.domain;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.GroupBuyListFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.OrderResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMenuTest {
    private final User user = UserFixture.fixture().build();
    private final Restaurant restaurant = RestaurantFixture.fixture(user).build();
    private final GroupBuyList groupBuyList = GroupBuyListFixture.fixture(user, restaurant).build();
    private final FoodMenu foodMenu = FoodMenuFixture.fixture(restaurant).build();

    @Test
    void 최상의_생성_테스트() {
        OrderMenu orderMenu = new OrderMenu(5, "testLocation", user, groupBuyList, foodMenu);

        assertEquals(5, orderMenu.getCount());
        assertEquals("testLocation", orderMenu.getLocation());
        assertEquals(25000, orderMenu.getPrice());
        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertEquals(user, orderMenu.getUser());
        assertEquals(groupBuyList, orderMenu.getGroupBuyList());
        assertEquals(foodMenu, orderMenu.getFoodMenu());
    }
}