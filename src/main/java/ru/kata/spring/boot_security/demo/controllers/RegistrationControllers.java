package ru.kata.spring.boot_security.demo.controllers;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;

@Controller
public class RegistrationControllers {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

//    @GetMapping("/registration")
//    public String registration(Model model) {
//        User user = new User();
//        Set<Role> allRoles = roleService.findAllRoles();
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", allRoles);
//        return "admin/registration";
//    }

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
