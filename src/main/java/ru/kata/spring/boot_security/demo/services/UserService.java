package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public interface UserService {
//
    User findById(Long id);
//
    List<User> findAll();
//
    void save (User user);
//
    void delete(String username);
//
    void update(User user);

    User findByUsername(String username);
}
