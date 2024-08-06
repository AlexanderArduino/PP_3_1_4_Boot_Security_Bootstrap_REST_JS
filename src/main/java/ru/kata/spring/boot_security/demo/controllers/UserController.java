package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserDetailServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public String goToUserPage(Model model, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
//        List<Role> roles = user.getRoles();
//        model.addAttribute("roles", roles);
        return "/user/user";
    }

    @GetMapping("/edituserbyid")
    public String editUserView(@RequestParam("id") Long id, Model model) {
        User user = userService.getById(id);
        System.out.println(user);
        model.addAttribute("user", user);
        return "/user/edit-user";
    }

    @PostMapping("/userupdate")
    public String updateUser(@ModelAttribute("user") User user, Model model){
        User user1 = user;
        user1.setRoles(roleService.findRolesByUserId(user.getId()));
        model.addAttribute("user", user1);
        userService.update(user1);
        return "redirect:/user";
    }

    @GetMapping("/deleteuserbyid")
    public String deleteUserAccount(@RequestParam("id") Long id){
//        List<Role> roles = roleService.findRolesByUserId(id);
//        roleService.deleteAllUserRoles(roles);
        User user = userService.getById(id);
        user.setRoles(new ArrayList<>());
        userService.delete(id);
        return "redirect:/registration";
    }

}

