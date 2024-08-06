package ru.kata.spring.boot_security.demo.repositories;


import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

public interface RoleDAO {

    Set<Role> findRoleByUserId(User user);
}
