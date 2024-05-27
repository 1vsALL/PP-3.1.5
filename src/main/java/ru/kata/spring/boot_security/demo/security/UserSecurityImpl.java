package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Optional;

@Service

public class UserSecurityImpl implements UserSecurity {
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> updatedUser = userRepository.findByEmail(username);
        if (updatedUser.isEmpty()) {
            throw new UsernameNotFoundException("Нет такого");
        }
        return updatedUser.get();
    }
}
