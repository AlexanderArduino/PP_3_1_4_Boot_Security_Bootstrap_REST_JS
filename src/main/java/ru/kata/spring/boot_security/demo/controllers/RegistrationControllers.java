package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class RegistrationControllers {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("user") User user,
                                   @ModelAttribute("adminRole") String adminRole,
                                   @ModelAttribute("userRole") String userRole,
                                   Model model) {
        if (userService.isUserExist(user)) {
            model.addAttribute("error",
                    String.format("Пользователь с логином %s уже занят", user.getUsername()));
            return "admin/error-registration";
        } else {
            userService.create(user);
            return "redirect:/admin/all-users";
        }
    }
}
