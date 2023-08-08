package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantAddReq;
import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantUpdateReq;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.RestaurantDeleter;
import com.example.foodcloud.domain.restaurant.service.RestaurantRegister;
import com.example.foodcloud.domain.restaurant.service.RestaurantUpdater;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import com.example.foodcloud.security.login.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantRepository repository;

    private final RestaurantRegister register;
    private final RestaurantUpdater updater;
    private final RestaurantDeleter deleter;

    @GetMapping("/add")
    public String showAddForm() {
        return "thymeleaf/restaurant/add";
    }

    @PostMapping("/add")
    public String add(@Valid RestaurantAddReq req) {
        Long userId = getCurrentUserId();

        register.register(userId, req.convert());
        return "thymeleaf/restaurant/add";
    }

    @GetMapping("/list")
    public String showRestaurantList(Model model) {
        Long userId = getCurrentUserId();

        model.addAttribute("restaurantList", repository.findByUserId(userId));
        return "thymeleaf/restaurant/list";
    }

    @GetMapping("/update/{restaurantId}")
    public String showUpdateForm(@PathVariable Long restaurantId, Model model) {
        Long userId = getCurrentUserId();

        Restaurant restaurant = findByUserIdAndId(userId, restaurantId);
        model.addAttribute("restaurantInfo", restaurant);
        return "thymeleaf/restaurant/update";
    }

    @PutMapping("/{restaurantId}")
    public String update(@PathVariable Long restaurantId, @Valid RestaurantUpdateReq req) {
        Long userId = getCurrentUserId();

        updater.update(userId, restaurantId, req.convert());

        return "redirect:/restaurant/update/" + restaurantId;
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam Long restaurantId, Model model) {
        Long userId = getCurrentUserId();

        Restaurant restaurant = findByUserIdAndId(userId, restaurantId);
        model.addAttribute("restaurantInfo", restaurant);
        return "thymeleaf/restaurant/delete";
    }

    @DeleteMapping("/{restaurantId}")
    public String delete(@PathVariable Long restaurantId, String password) {
        Long userId = getCurrentUserId();

        deleter.delete(userId, password, restaurantId);

        return "redirect:/restaurant/list";
    }

    private Restaurant findByUserIdAndId(Long userId, Long restaurantId){
        return repository.findByUserIdAndId(userId, restaurantId).orElseThrow(() ->
                new NotFoundRestaurantException("Not found Restaurant"));
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