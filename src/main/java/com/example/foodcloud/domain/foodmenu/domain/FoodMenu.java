package com.example.foodcloud.domain.foodmenu.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 음식의 이름 */
    @Column(nullable = false)
    private String name;

    /** 음식의 가격 */
    @Column(nullable = false)
    private int price;

    /** 음식의 주문 횟수 */
    @Column(nullable = false)
    private int orderCount;

    /** 음식의 온도 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Temperature temperature;

    /** 음식의 타입 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodTypes foodTypes;

    /** 음식의 고기 종류 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MeatTypes meatType;

    /** 음식의 야채 양 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Vegetables vegetables;

    /** 음식의 이미지 path */
    private String imagePath;

    /** 음식이 등록된 식당 */
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    protected FoodMenu() {
    }

    /** 기본 생성자 */
    public FoodMenu(String name, int price, Temperature temperature, FoodTypes foodTypes, MeatTypes meatType, Vegetables vegetables, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.temperature = temperature;
        this.foodTypes = foodTypes;
        this.meatType = meatType;
        this.vegetables = vegetables;
        this.restaurant = restaurant;
    }

    /** 업데이트 메소드 */
    public void update(String foodMenuName, int price, Temperature temperature, FoodTypes foodTypes, MeatTypes meatType, Vegetables vegetables) {
        this.name = foodMenuName;
        this.price = price;
        this.temperature = temperature;
        this.foodTypes = foodTypes;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }

    /** 주문 횟수 증가 */
    public void incrementOrderCount() {
        this.orderCount++;
    }

    /** 이미지 path 업데이트 */
    public void uploadImage(String imagePath) {
        this.imagePath = imagePath;
    }
}