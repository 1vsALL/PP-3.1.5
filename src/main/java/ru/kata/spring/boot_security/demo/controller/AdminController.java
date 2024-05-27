package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserSecurity;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;
    private final UserSecurity userSecurity;


    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService, UserSecurity userSecurity) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
        this.userSecurity = userSecurity;
    }

    @GetMapping()
    public String users(ModelMap modelMap, Principal principal) {
        User user = (User) userSecurity.loadUserByUsername(principal.getName());
        boolean security = user.getRolesString().contains("ADMIN");
        modelMap.addAttribute("user", userService.userID(user.getId()));
        modelMap.addAttribute("userList", userService.listUsers());
        modelMap.addAttribute("rollers", roleService.getRoles());
        modelMap.addAttribute("security", security);
        return "users";
    }

    @GetMapping("/add")
    public String newUser(ModelMap modelMap, Principal principal) {
        User user = (User) userSecurity.loadUserByUsername(principal.getName());
        boolean security = user.getRolesString().contains("ADMIN");
        modelMap.addAttribute("user", userService.userID(user.getId()));
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("security", security);
        return "add";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("userAdded") @Validated User user,
                             BindingResult bindingResult, ModelMap modelMap) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "add";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/remove")
    public String removeUser(@RequestParam("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.userID(id));
        modelMap.addAttribute("role", roleService.getRoles());
        return "usersEdit";
    }


    @PostMapping("/update")
    public String update(@RequestParam("id") long id, @ModelAttribute("human") User user, ModelMap modelMap) {
        modelMap.addAttribute("roles", roleService.getRoles());
        userService.update(user, id);
        return "redirect:/admin";
    }

}
