// UserController.java
package com.dailycodework.sbend2endapplication.user;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;


    @GetMapping
    public String getUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Optional<User> user = userService.findById(id);
        model.addAttribute("user", user.get());
        return "update-user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        return "redirect:/users/profile";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, User user){
        userService.updateUser(id, user.getFirstName(), user.getLastName(), user.getEmail());
        return "redirect:/users?update_success";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        return "profile";
    }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/users?delete_success";
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String username = principal.getName();
            User user = userService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Sauvegarde de l'image avec le nom de l'utilisateur
            userService.uploadUserImage(file, user.getId());

            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            log.error("Error uploading image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }

    }



}
