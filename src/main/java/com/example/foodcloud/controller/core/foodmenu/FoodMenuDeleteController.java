package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.delete.FoodMenuDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * 메뉴를 삭제하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/delete")
public class FoodMenuDeleteController {
    private final FoodMenuDeleteService foodMenuDeleteService;
    private final FoodMenuRepository foodMenuRepository;

    /**
     * RequestParam를 통해 foodMenuId를 받아옴
     * foodMenuId를 통해 foodMenu의 정보를 출력해줌
     */
    @GetMapping("")
    public String get(@RequestParam Long foodMenuId, Model model) {
        foodMenuRepository.findById(foodMenuId).ifPresent(foodMenu ->
                model.addAttribute("foodMenu", foodMenu));

        return "thymeleaf/food-menu/delete";
    }

    /**
     * RequestParm을 통해 foodMenuId 받아옴.
     * 세션을 통해 userId를 받아옴.
     * String으로 password를 받아와서 처리함
     */
    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long foodMenuId,
                       String password,
                       Model model) {
        model.addAttribute("isDelete", foodMenuDeleteService.delete(userId, foodMenuId, password));

        return "thymeleaf/food-menu/delete";
    }
}
