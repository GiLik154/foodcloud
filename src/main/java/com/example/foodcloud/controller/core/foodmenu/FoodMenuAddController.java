package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.controller.core.foodmenu.dto.FoodMenuAddControllerDto;
import com.example.foodcloud.domain.foodmenu.service.add.FoodMenuAddService;
import com.example.foodcloud.enums.FoodType;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/add")
public class FoodMenuAddController {
    private final FoodMenuAddService foodMenuAddService;

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("foodTypes", FoodType.values());
        return "thymeleaf/food-menu/add";
    }

    @GetMapping("/{type}")
    @ResponseBody
    public List<String> getFoodKinds(@PathVariable("type") FoodType type) {
        List<FoodTypes> kinds = type.getKinds();
        return kinds.stream().map(FoodTypes::getName).collect(Collectors.toList());
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                        Long restaurantId,
                        @Valid FoodMenuAddControllerDto foodMenuAddControllerDto,
                        MultipartFile file,
                        Model model) {

        model.addAttribute("isAdd", foodMenuAddService.add(userId, restaurantId, foodMenuAddControllerDto.convert(), file));

        return "thymeleaf/food-menu/add";
    }

}
