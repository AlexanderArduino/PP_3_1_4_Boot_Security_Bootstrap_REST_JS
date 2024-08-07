package ru.kata.spring.boot_security.demo.TestingUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class Init {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
//

    @PostConstruct
    @Transactional
    public void init() {
        // Создание ролей
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        List<Role> userRoleList = new ArrayList<>();
        userRoleList.add(userRole);
        User regularUser = new User("user", "user", "user@mail.ru", "user", "user", userRoleList);
        userRepository.save(regularUser);

        List<Role> adminRoleList = new ArrayList<>();
        adminRoleList.add(adminRole);
        User adminUser = new User("admin", "admin", "admin@mail.ru", "admin", "admin", adminRoleList);
        userRepository.save(adminUser);

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
