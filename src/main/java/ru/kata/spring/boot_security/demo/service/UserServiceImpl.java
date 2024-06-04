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
        if (userRepository.findByEmail(user.getUsername()).isEmpty()) {
            User updatedUser = new User();
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updatedUser.setAge(user.getAge());
            updatedUser.setId(user.getId());
            updatedUser.setName(user.getName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setRoles(user.getRoles());
            userRepository.save(updatedUser);
        } else {
            throw new EntityNotFoundException("не получилось добавить");
        }
    }

    @Override
    public User userID(long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseThrow(() -> new EntityNotFoundException("не получилось получить, получается"));
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("не получилось удалить");
        }
    }

    @Transactional
    @Override
    public void update(User user, long id) {
        if (userRepository.findById(id).isPresent()) {
            User updatedUser = userRepository.findById(id).get();
            updatedUser.setName(user.getName());
            updatedUser.setRoles(user.getRoles());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setAge(user.getAge());
            if (!user.getPassword().isEmpty()) {
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            throw new EntityNotFoundException("не получилось обновить");
        }
    }
}
