package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserSecurity;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class AdminInit {

    private final UserRepository userRepository;
    private final UserSecurity userSecurity;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Autowired
    public AdminInit(UserRepository userRepository, UserSecurity userSecurity, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.userSecurity = userSecurity;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @PostConstruct
    public void Init() {
        User administrator = new User("admin",
                "admin",
                0,
                "admin",
                "admin@admin.ru",
                Collections.emptyList());
        if (userRepository.findByEmail(administrator.getEmail()).isEmpty()) {
            Role admin = new Role();
            Role user = new Role();
            admin.setName("ROLE_ADMIN");
            user.setName("ROLE_USER");
            roleService.saveRole(admin);
            roleService.saveRole(user);
            administrator.setRoles(List.of(admin, user));
            administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
            userRepository.save(administrator);
        }
    }
}



