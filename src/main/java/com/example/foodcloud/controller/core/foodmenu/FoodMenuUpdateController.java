package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.controller.core.foodmenu.dto.FoodMenuUpdateControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.FoodMenuUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/update")
public class FoodMenuUpdateController {
    private final FoodMenuUpdateService foodMenuUpdateService;
    private final FoodMenuRepository foodMenuRepository;

    @GetMapping("")
    public String get(@RequestParam Long foodMenuId, Model model) {
        foodMenuRepository.findById(foodMenuId).ifPresent(foodMenu ->
                model.addAttribute("foodMenuInfo", foodMenu));


        return "thymeleaf/food-menu/update";
    }

    @PostMapping("")
    public String post(Long foodMenuId, Long restaurantId, @Valid FoodMenuUpdateControllerDto foodMenuUpdateControllerDto, MultipartFile multipartFile, Model model) throws IOException {

        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);

        model.addAttribute("isUpdate",
                foodMenuUpdateService.update(foodMenuId,
                        restaurantId,
                        foodMenuUpdateControllerDto.convert(),
                        file));

        return "thymeleaf/food-menu/update";
    }
}
