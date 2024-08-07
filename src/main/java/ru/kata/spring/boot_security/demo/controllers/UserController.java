package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserDetailServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserDetailServiceImpl(UserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    @GetMapping("/user")
    public String goToUserPage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        String username = currentUser.getUsername();
        User user = userService.getByUsername(username);
        model.addAttribute("user", user);
        return "/user";
    }
}

