package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> listUsers();

    void addUser(User user);

    User userID(long id);

    void removeUserById(long id);

    void update(User user, long id);
}
