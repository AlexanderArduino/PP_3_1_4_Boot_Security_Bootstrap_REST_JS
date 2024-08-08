package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

@Controller
public class LoginController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String mainPage() {
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (bCryptPasswordEncoder.encode(password).equals(user.getPassword())) {
                model.addAttribute("user", user);
                return "redirect:/user";
            }
        }
        return "redirect:/login";
    }
//        String username = currentUser.getUsername();
//        User user = userService.getByUsername(username);
//        model.addAttribute("user", user);
//            return "redirect:/user";
//    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

}
