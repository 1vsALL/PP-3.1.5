package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User userID(long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void update(User user, long id) {
        User updatedUser = userRepository.findById(id).get();
        updatedUser.setId(id);
        updatedUser.setRoles(user.getRoles());
        updatedUser.setName(user.getName());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(updatedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> updatedUser = userRepository.findByName(username);
        if (updatedUser.isEmpty()) {
            throw new UsernameNotFoundException("Нет такого");
        }
        return new User(updatedUser.get().getName(), updatedUser.get().getPassword()
                , (Collection<Role>) mapRolesToAuthorities(updatedUser.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
