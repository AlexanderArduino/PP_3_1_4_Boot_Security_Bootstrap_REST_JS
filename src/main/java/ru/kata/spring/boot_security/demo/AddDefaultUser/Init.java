package ru.kata.spring.boot_security.demo.AddDefaultUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Init {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
//

    @PostConstruct
    @Transactional
    public void init() {
        // Создание ролей
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        Set<Role> userRoleList = new HashSet<>();
        userRoleList.add(userRole);
        User regularUser = new User("user", "user", "user@mail.ru", "user", "user", userRoleList);
        userService.create(regularUser);

        Set<Role> adminRoleList = new HashSet<>();
        adminRoleList.add(adminRole);
        User adminUser = new User("admin", "admin", "admin@mail.ru", "admin", "admin", adminRoleList);
        userService.create(adminUser);

//        User adminUser = new User();
//        adminUser.setName("Admin");
//        adminUser.setSurname("Adminov");
//        adminUser.setEmail("admin@example.com");
//        adminUser.setUsername("admin");
//        adminUser.setPassword("admin");
//        List<Role> adminRoleList = new ArrayList<>();
//        adminRoleList.add(adminRole);
//        adminUser.setRoles(adminRoleList);  // Присвоение роли ADMIN
//        userRepository.save(adminUser);

        // Сохранение в связующую таблицу произойдет автоматически благодаря аннотации @ManyToMany
    }
}
