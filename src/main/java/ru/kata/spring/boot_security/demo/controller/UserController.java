//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.security.UserSecurity;
//
//import java.security.Principal;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//
//    private final UserSecurity userSecurity;
//
//    @Autowired
//    public UserController(UserSecurity userSecurity) {
//        this.userSecurity = userSecurity;
//    }
//
//
//    @GetMapping()
//    public String showSimpleUser(Principal principal, ModelMap modelMap) {
//        User user = (User) userSecurity.loadUserByUsername(principal.getName());
//        boolean security = user.getRolesString().contains("ADMIN");
//        modelMap.addAttribute("user", user);
//        modelMap.addAttribute("security", security);
//        return "userShow";
//    }
//
//}
