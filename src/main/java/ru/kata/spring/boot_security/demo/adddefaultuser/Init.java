package ru.kata.spring.boot_security.demo.adddefaultuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    @Autowired
    private UserService userService;

    @PostConstruct
    @Transactional
    public void init() {
        // Создание ролей
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        // Создание user
        Set<Role> userRoleList = new HashSet<>();
        userRoleList.add(userRole);
        User regularUser = new User("user", "user", "user@mail.ru", "user", "user", userRoleList);
        userService.create(regularUser);

        // Создание admin
        Set<Role> adminRoleList = new HashSet<>();
        adminRoleList.add(adminRole);
        User adminUser = new User("admin", "admin", "admin@mail.ru", "admin", "admin", adminRoleList);
        userService.create(adminUser);
    }
}
