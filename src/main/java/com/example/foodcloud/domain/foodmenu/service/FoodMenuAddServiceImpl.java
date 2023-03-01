package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuAddDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuAddServiceImpl implements FoodMenuAddService {
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;

    public boolean add(Long userId, Long restaurantId, FoodMenuAddDto foodMenuAddDto, MultipartFile file) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);
        if (restaurantOptional.isPresent()) {
            FoodMenu foodMenu = new FoodMenu(foodMenuAddDto.getFoodMenu(),
                    foodMenuAddDto.getPrice(),
                    foodMenuAddDto.getFoodType(),
                    foodMenuAddDto.getTemperature(),
                    foodMenuAddDto.getMeatType(),
                    foodMenuAddDto.getVegetables(),
                    getFilePath(restaurantId, file),
                    restaurantOptional.get()
            );

            foodMenuRepository.save(foodMenu);

            return true;
        }
        return false;
    }

    private String getFilePath(Long restaurantId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(creatFileName());
        String uploadDir = "food-menu-images/" + getRestaurantName(restaurantId) + "/";
        Path uploadPath = Paths.get(uploadDir);

        checkDirectories(uploadPath);

        uploadImage(file, uploadPath, fileName);

        return uploadDir + fileName;
    }

    private String creatFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS");
        return now.format(formatter);
    }

    private String getRestaurantName(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isPresent()) {
            return restaurantOptional.get().getName();
        }

        throw new NotFoundRestaurantException();
    }

    private void checkDirectories(Path uploadPath) {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(MultipartFile file, Path uploadPath, String fileName) {
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
