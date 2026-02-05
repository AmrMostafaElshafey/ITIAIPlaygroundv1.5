package com.iti.aiplayground.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadStorageService {
    private final Path uploadRoot;

    public UploadStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        Files.createDirectories(uploadRoot);
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path destination = uploadRoot.resolve(filename);
        Files.copy(file.getInputStream(), destination);
        return destination.toString();
    }
}
