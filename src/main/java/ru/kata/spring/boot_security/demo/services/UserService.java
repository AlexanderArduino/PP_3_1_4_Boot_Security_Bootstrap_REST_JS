package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void create(User user);

    void update(User user);

    void delete(Long id);

    User findById(Long id);

    List<User> listUsers();
}