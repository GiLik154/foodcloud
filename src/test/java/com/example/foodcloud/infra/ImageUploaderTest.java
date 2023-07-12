package com.example.foodcloud.infra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImageUploaderTest {
    private final ImageUploader imageUploader;

    @Autowired
    public ImageUploaderTest(ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
    }

    @AfterEach
    public void deleteFile() throws IOException {
        String path = "food-menu-images/testImageUpload/";
        File folder = new File(path);

        Path uploadPath = Paths.get(path);
        Files.createDirectories(uploadPath);
        File[] deleteFolderList = folder.listFiles();

        for (File f : deleteFolderList) {
            f.delete();
        }

        folder.delete();
    }

    @Test
    void 이미지_업로드_정상작동() throws IOException {
        String path = "food-menu-images/testImageUpload/";
        File folder = new File(path);
        Path uploadPath = Paths.get("food-menu-images/testImageUpload/");
        Files.createDirectories(uploadPath);

        File file = new File("test.jpg");
        file.createNewFile();

        imageUploader.savedFileAndReturnFilePath("testImageUpload", file);

        assertEquals(1, folder.listFiles().length);
    }

    @Test
    void 이미지_업로드_정상작동_디렉토리없음() throws IOException {
        String path = "food-menu-images/testImageUpload/";
        File folder = new File(path);

        Path uploadPath = Paths.get(path);
        Files.createDirectories(uploadPath);
        File[] deleteFolderList = folder.listFiles();

        for (File f : deleteFolderList) {
            f.delete();
        }

        folder.delete();

        File file = new File("test.jpg");
        file.createNewFile();

        imageUploader.savedFileAndReturnFilePath("testImageUpload", file);

        assertEquals(1, folder.listFiles().length);
    }
}