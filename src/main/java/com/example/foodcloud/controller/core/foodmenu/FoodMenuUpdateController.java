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

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/update")
public class FoodMenuUpdateController {
    private final FoodMenuUpdateService foodMenuUpdateService;
    private final FoodMenuRepository foodMenuRepository;

    @GetMapping("/{foodMenuId}")
    public String get(@PathVariable Long foodMenuId, Model model) {
        foodMenuRepository.findById(foodMenuId).ifPresent(foodMenu ->
                model.addAttribute("foodMenu", foodMenu));


        return "thymeleaf/food-menu/update";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * PathVariable를 통해 foodMenuId를 받아옴
     * 이후 Dto를 받아오고 @Valid로 검증함
     * MultipartFile를 File로 바꿔주고 서비스단으로 내림.
     */
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

        foodMenuUpdateService.update(foodMenuId,
                foodMenuUpdateControllerDto.convert(),
                file);

        return "thymeleaf/food-menu/update";
    }
}
