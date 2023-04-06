package com.example.foodcloud.domain.foodmenu.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
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
    public String saveFileAndReturnFilePath(String restaurantName, File file) {
        String fileName = StringUtils.cleanPath(creatFileName());
        String uploadDir = "food-menu-images/" + restaurantName + "/";
        Path uploadPath = Paths.get(uploadDir);

        createDirectoryIfNotExist(uploadPath);

        uploadImage(file, uploadPath, fileName);

        return "/" + uploadDir + fileName;
    }

    /**
     * 이미지의 파일 이름을 업로드 시간으로 생성한다.
     * 중복을 피하기 위해 나노초 까지 사용한다.
     */
    private String creatFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSSS");
        return now.format(formatter);
    }

    /**
     * 폴더가 있는지 확인하고, 없을 시에 폴더를 생성한다.
     * 폴더의 이름은 식당의 이름으로 주어진다
     *
     * @param uploadPath 업로드 될 경로
     */
    private void createDirectoryIfNotExist(Path uploadPath) {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 이미지를 업로드한다.
     *
     * @param file       업로드 할 파일
     * @param uploadPath 업로드 될 경로
     * @param fileName   업로드 될 파일 이름
     */
    private void uploadImage(File file, Path uploadPath, String fileName) {
        try (InputStream inputStream = new FileInputStream(file)) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
