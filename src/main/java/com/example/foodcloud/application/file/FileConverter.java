package com.example.foodcloud.application.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileConverter {
    File convert(MultipartFile multipartFile) throws IOException;
}
