package com.dailycodework.sbend2endapplication.user;

import com.dailycodework.sbend2endapplication.registration.token.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.Optional;

/**
 * @author Sampson Alfred
 */
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
}
