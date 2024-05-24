package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class AdminInit {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminInit(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostConstruct
    public void Init() {
        List<Role> example=new ArrayList<>();
//        example.add(new Role("ADMIN"));
        User admin = new User("adminka",
                passwordEncoder.encode("admin"),
                example
                );
        userService.addUser(admin);
    }
}
