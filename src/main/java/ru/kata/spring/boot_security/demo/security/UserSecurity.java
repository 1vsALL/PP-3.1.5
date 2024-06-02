package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;


public interface UserSecurity extends UserDetailsService {
     Optional<User> findByUsername(String username);
}
