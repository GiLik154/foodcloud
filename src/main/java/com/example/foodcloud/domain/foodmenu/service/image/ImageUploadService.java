package com.example.foodcloud.domain.foodmenu.service.image;

import java.io.File;

public interface ImageUploadService {
    String saveFileAndReturnFilePath(String restaurantName, File file);
}
