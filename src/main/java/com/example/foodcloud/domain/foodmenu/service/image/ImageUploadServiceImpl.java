package com.example.foodcloud.domain.foodmenu.service.image;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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

@Component
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {
    @Override
    public void upload(String restaurantName, MultipartFile file, FoodMenu foodMenu) {
        String fileName = StringUtils.cleanPath(creatFileName());
        String uploadDir = "food-menu-images/" + restaurantName + "/";
        Path uploadPath = Paths.get(uploadDir);

        checkDirectories(uploadPath);

        uploadImage(file, uploadPath, fileName);

        foodMenu.uploadImage("/" + uploadDir + fileName);
    }

    private String creatFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS");
        return now.format(formatter);
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
