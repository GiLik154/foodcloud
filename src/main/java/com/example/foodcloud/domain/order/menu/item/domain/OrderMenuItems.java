package com.example.foodcloud.domain.order.menu.item.domain;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;
import com.fasterxml.jackson.databind.deser.BasicDeserializerFactory;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
public class OrderMenuItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0)
    private int count;
    @Min(value = 0)
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;

    public OrderMenuItems() {
    }

    public OrderMenuItems(int count, FoodMenu foodMenu) {
        this.count = count;
        this.price = Math.multiplyExact(foodMenu.getPrice(), count);
        this.foodMenu = foodMenu;
    }
}


//