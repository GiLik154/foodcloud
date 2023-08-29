package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.enums.FoodType;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/add")
public class FoodMenuTypeRestController {

    @GetMapping("/{type}")
    public List<String> getFoodKinds(@PathVariable("type") FoodType type) {
        List<FoodTypes> kinds = type.getKinds();
        return kinds.stream().map(FoodTypes::getName).collect(Collectors.toList());
    }
}
