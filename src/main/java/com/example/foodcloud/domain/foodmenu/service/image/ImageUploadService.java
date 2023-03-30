package com.example.foodcloud.domain.foodmenu.service.image;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageUploadService {
    String saveFileAndReturnFilePath(String restaurantName, File file);
}
