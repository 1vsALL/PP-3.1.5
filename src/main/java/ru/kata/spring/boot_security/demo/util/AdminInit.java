package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminInit {

    private final UserServiceImpl userService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AdminInit(UserServiceImpl userService, UserDetailsService userDetailsService) {
        this.userService = userService;

        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void Init() {
        List<Role> example = new ArrayList<>();
        Role role=new Role("ADMIN");
        User admin = new User("adminka",
                "admin"

        );
        admin.addRole("ADMIN");

        userService.addUser(admin);
    }
}
