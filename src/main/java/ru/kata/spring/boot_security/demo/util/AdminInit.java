package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class AdminInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void Init() {
        Role role = new Role();
        role.setId(1L);
        User admin = new User("admin", "admin", 200, "admin", "admin@admin.ru", Collections.singletonList(role));
        if (userRepository.findByEmail(admin.getEmail()).isEmpty()) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            userRepository.save(admin);
        }
    }
}



