package com.example.foodcloud.domain.foodmenu.service.image;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    void upload(String restaurantName, MultipartFile file, FoodMenu foodMenu);
}
