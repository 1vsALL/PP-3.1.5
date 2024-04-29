package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;

@org.springframework.stereotype.Controller
public class Controller {
    private UserRepository userRepository;

    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/user")
    public String userInfo(Model model, Principal principal){
            return "user";
    }
    @GetMapping(value = "/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }

    @GetMapping(value = "/add")
    public String addUser(Model model) {
        System.out.println("add__________________");
        User user = new User();
        model.addAttribute("user", user);
        return "editUser";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "editUser";
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute User user) {

        userRepository.save(user);

        return "redirect:/admin";
    }

}

