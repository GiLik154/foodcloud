package com.example.foodcloud;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.payment.domain.Payment;
import com.example.foodcloud.domain.user.domain.User;

public class OrderMenuFixture {
    /** 음식 주문 갯수 */
    private int count = 10;

    /** 주문 장소 */
    private String location = "testLocation";

    /** 주문한 유저 */
    private User user;

    /** OrderMenu를 공구하는 메인 주문 리스트 */
    private GroupBuyList groupBuyList;

    /** Order에 담겨있는 FoodMenu */
    private FoodMenu foodMenu;

    private OrderMenuFixture(User user, GroupBuyList groupBuyList, FoodMenu foodMenu) {
        this.user = user;
        this.groupBuyList = groupBuyList;
        this.foodMenu = foodMenu;
    }

    public static OrderMenuFixture fixture(User user, GroupBuyList groupBuyList, FoodMenu foodMenu) {
        return new OrderMenuFixture(user, groupBuyList, foodMenu);
    }

    public OrderMenuFixture count(int count) {
        this.count = count;
        return this;
    }

    public OrderMenuFixture location(String location) {
        this.location = location;
        return this;
    }

    public OrderMenuFixture user(User user) {
        this.user = user;
        return this;
    }

    public OrderMenuFixture groupBuyList(GroupBuyList groupBuyList) {
        this.groupBuyList = groupBuyList;
        return this;
    }

    public OrderMenuFixture foodMenu(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
        return this;
    }

    public OrderMenu build() {
        return new OrderMenu(this.count, this.location, this.user, this.groupBuyList, this.foodMenu);
    }
}
