package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Controller
public class LoginController {

    @GetMapping("/")
    public String mainPage() {
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
