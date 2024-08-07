package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
//        System.out.println("update start");
//        User user1 = userRepository.findByUsername(user.getUsername());
//        System.out.println(user1);
//        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
