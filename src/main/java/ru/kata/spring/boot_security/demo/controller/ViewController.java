package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping(value = "/admin")
    public String usersList(Model model, Authentication authentication) {
        return "users";
    }

    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication) {
        return "users";
    }
}
