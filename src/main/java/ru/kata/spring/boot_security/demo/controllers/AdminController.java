package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

import java.security.Principal;
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
    public String allUsers(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        List<User> users = userService.findAllUsers();
        String username = currentUser.getUsername();
        User mainUser = userService.getByUsername(username);
        Collection<GrantedAuthority> roles = currentUser.getAuthorities();
        model.addAttribute("roles", roles);
        model.addAttribute("username", username);
        model.addAttribute("mainUser", mainUser);
        model.addAttribute("users", users);
        User user = new User();
        Set<Role> allRoles = roleService.findAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "/admin/all-users";
    }


    @GetMapping("/edituserbyid")
    public String editUserView(@RequestParam("id") Long id, Model model) {
        User user = userService.getById(id);
        Set<Role> allRoles = roleService.findAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "admin/edit-user";
    }

    @PostMapping("/userupdate")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/deleteuserbyid")
    public String deleteUserAccount(@RequestParam("id") Long id) {
        User user = userService.getById(id);
        user.setRoles(new HashSet<>());
        userService.delete(id);
        return "redirect:/admin/all-users";
    }
}
