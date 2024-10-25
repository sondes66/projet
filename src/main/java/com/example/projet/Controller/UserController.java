package com.example.projet.Controller;

import com.example.projet.Entity.User;
import com.example.projet.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {

        Path imagePath = Paths.get("uploads/", profileImage.getOriginalFilename());
        Files.write(imagePath, profileImage.getBytes());

        User updatedUser = userService.updateProfile(username, email, imagePath.toString());
        return ResponseEntity.ok(updatedUser);
    }
}
