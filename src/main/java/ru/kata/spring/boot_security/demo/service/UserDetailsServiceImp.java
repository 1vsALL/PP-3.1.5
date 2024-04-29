package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.Optional;

//Этот класс нужен для того, чтобы отдать SpringSec UserDetails в кфг
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    //SpringSec ищет данный метод и передает на сверку юзернэйм, если он есть и все ок,
    // то мы возвращаем ему UserDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDb = userRepository.findByUsername(username);
        if (userDb.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Пользователя с ником '%s', нет в базе", username));
        }
        return userDb.get();
    }
}
