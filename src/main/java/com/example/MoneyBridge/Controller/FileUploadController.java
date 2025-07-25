package com.example.MoneyBridge.Controller;

import com.example.MoneyBridge.Models.User;
import com.example.MoneyBridge.Services.FileStorageService;
import com.example.MoneyBridge.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private UserService userService;

    @PostMapping("/users/upload-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();

        User updatedUser = userService.updateProfilePicture(phoneNumber, fileDownloadUri);
        return ResponseEntity.ok(Map.of("url", updatedUser.getProfilePictureUrl()));
    }
}