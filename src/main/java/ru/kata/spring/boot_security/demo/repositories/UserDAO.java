package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user);

    void update(User user);

    User findById(Long id);

    User finByUsername(String username);

    void deleteUser(String username);

    List<User> findAll();
}
