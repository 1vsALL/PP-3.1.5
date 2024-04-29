package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;

@org.springframework.stereotype.Controller
public class Controller {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Controller(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/user")
    public String userInfo(Model model, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
            return "user";
    }
    @GetMapping(value = "/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    @GetMapping(value = "/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleRepository.findAll());
        return "editUser";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        User user = userRepository.getById(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("userRoles", roleRepository.findAll());
        return "editUser";
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/admin";
    }

}

