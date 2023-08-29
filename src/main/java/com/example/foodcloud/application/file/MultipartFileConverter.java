package com.example.foodcloud.application.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class MultipartFileConverter implements FileConverter {

    @Override
    public File convert(MultipartFile multipartFile) throws IOException {
        File file = null;

        if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            file = new File(originalFilename);
            multipartFile.transferTo(file);
        }

        return file;
    }
}
