package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        if (userRepository.findByName(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User userID(long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseThrow();
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Override
    public void update(User user, long id) {
        User updatedUser = userRepository.findById(id).get();
        updatedUser.setName(user.getName());
        updatedUser.setRoles(user.getRoles());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> updatedUser = userRepository.findByName(username);
        if (updatedUser.isEmpty()) {
            throw new UsernameNotFoundException("Нет такого");
        }
        return updatedUser.get();
    }

}
