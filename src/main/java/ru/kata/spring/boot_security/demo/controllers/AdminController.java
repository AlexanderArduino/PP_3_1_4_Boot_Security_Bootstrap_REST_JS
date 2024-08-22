package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.*;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all-users")
    public String getAllUsers(Model model,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        //для отображения в навбаре зашедшего пользователя
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        User mainUser = userService.getByUsername(currentUser.getUsername());
        model.addAttribute("mainUser", mainUser);
        //для вкладки нового пользователя
        User newUser = new User();
        Set<Role> allRoles = roleService.findAllRoles();
        model.addAttribute("newUser", newUser);
        model.addAttribute("allRoles", allRoles);
        return "/admin/all-users";
    }


    @GetMapping("/edit-user-by-id")
    public String editUserView(@RequestParam("id") Long id, Model model) {
        User editUser = userService.getById(id);
        Set<Role> allRoles = roleService.findAllRoles();
        model.addAttribute("user", editUser);
        model.addAttribute("allRoles", allRoles);
        return "admin/edit-user";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/delete-user-by-id")
    public String deleteUserAccount(@RequestParam("id") Long id) {
        User user = userService.getById(id);
        user.setRoles(new HashSet<>());
        userService.delete(id);
        return "redirect:/admin/all-users";
    }
}
