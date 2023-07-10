package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.controller.core.foodmenu.dto.FoodMenuUpdateControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/update")
public class FoodMenuUpdateController {
    private final FoodMenuUpdater foodMenuUpdater;
    private final FoodMenuRepository foodMenuRepository;

    @GetMapping("/{foodMenuId}")
    public String get(@PathVariable Long foodMenuId, Model model) {
        foodMenuRepository.findById(foodMenuId).ifPresent(foodMenu ->
                model.addAttribute("foodMenu", foodMenu));


        return "thymeleaf/food-menu/update";
    }

    @PostMapping("/{foodMenuId}")
    public String post(@PathVariable Long foodMenuId,
                       @Valid FoodMenuUpdateControllerDto foodMenuUpdateControllerDto,
                       MultipartFile multipartFile) throws IOException {

        File file = null;

        if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            file = new File(originalFilename);
            multipartFile.transferTo(file);
        }

        foodMenuUpdater.update(foodMenuId,
                foodMenuUpdateControllerDto.convert(),
                file);

        return "thymeleaf/food-menu/update";
    }
}
