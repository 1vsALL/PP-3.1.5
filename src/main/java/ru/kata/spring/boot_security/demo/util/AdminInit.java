package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class AdminInit {

    private final UserRepository userRepository;

    @Autowired
    public AdminInit( UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @PostConstruct
    @Transactional
    public void Init() {
    User user=new User("adminka","admin", Collections.emptyList());
    if(userRepository.findByName(user.getName()).isEmpty()){
        
        userRepository.save(user);
    }
    }
}
