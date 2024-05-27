package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
            User updatedUser = new User();
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updatedUser.setAge(user.getAge());
            updatedUser.setId(user.getId());
            updatedUser.setName(user.getName());
            updatedUser.setRoles(user.getRoles());
            userRepository.save(updatedUser);
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
        updatedUser.setAge(user.getAge());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
