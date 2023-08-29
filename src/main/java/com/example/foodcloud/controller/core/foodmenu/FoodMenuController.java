package com.example.foodcloud.controller.core.foodmenu;


import com.example.foodcloud.application.file.FileConverter;
import com.example.foodcloud.controller.core.foodmenu.req.FoodMenCreateReq;
import com.example.foodcloud.controller.core.foodmenu.req.FoodMenuUpdaterReq;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuCreator;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuDeleter;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuUpdater;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu")
public class FoodMenuController {
    private final FoodMenuRepository repository;

    private final FoodMenuCreator creator;
    private final FoodMenuUpdater updater;
    private final FoodMenuDeleter deleter;

    private final FileConverter converter;

    @GetMapping("/list")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("foodMenuList", repository.findByRestaurantId(restaurantId));

        return "thymeleaf/food-menu/list";
    }

    @GetMapping("/add")
    public String showCreatePage() {
        return "thymeleaf/food-menu/add";
    }

    @PostMapping("/{restaurantId}")
    public String post(@PathVariable Long restaurantId,
                       @Valid FoodMenCreateReq req,
                       MultipartFile multipartFile) throws IOException {
        Long userId = getCurrentUserId();

        creator.create(userId, restaurantId, req.convert(), converter.convert(multipartFile));

        return "thymeleaf/food-menu/add";
    }

    @GetMapping("update/{foodMenuId}")
    public String showUpdatePage(@PathVariable Long foodMenuId, Model model) {
        FoodMenu foodMenu = repository.findById(foodMenuId).orElseThrow(() ->
                new NotFoundFoodMenuException("Not found FoodMenu where showUpdatePage"));

        model.addAttribute("foodMenu", foodMenu);

        return "thymeleaf/food-menu/update";
    }

    @PostMapping("/update/{foodMenuId}")
    public String updater(@PathVariable Long foodMenuId,
                          @Valid FoodMenuUpdaterReq req,
                          MultipartFile multipartFile) throws IOException {
        updater.update(foodMenuId, req.convert(), converter.convert(multipartFile));

        return "redirect:/food-menu/update" + foodMenuId;
    }

    @GetMapping("/delete/{foodMenuId}")
    public String showDeletePage(@PathVariable Long foodMenuId, Model model) {
        repository.findById(foodMenuId).ifPresent(foodMenu ->
                model.addAttribute("foodMenu", foodMenu));

        return "thymeleaf/food-menu/delete";
    }

    @DeleteMapping("{foodMenuId}")
    public String post(@PathVariable Long foodMenuId,
                       String password) {
        Long userId = getCurrentUserId();

        deleter.delete(userId, foodMenuId, password);

        return "redirect:/food-menu/delete" + foodMenuId;
    }

    private Long getCurrentUserId() {
        UserDetail userDetail = getUserDetail();

        return userDetail.getUserId();
    }

    private UserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetail) authentication.getPrincipal();
    }
}
