package com.example.krishisaathi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


//for Drivers related photo registration
@Service
public class FileUploadService {

    private final Path uploadDir = Paths.get("src/main/resources/static/uploads/DriversDetail"); // main uploads folder

    public FileUploadService() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot save empty file.");
        }

        // Generate a unique file name (UUID prevents conflicts)
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);

        // Copy the file to /static/uploads
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative path to be stored in DB
        return "/uploads/" + fileName;
    }
}
