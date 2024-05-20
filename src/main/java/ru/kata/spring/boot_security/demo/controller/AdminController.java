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
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;



@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleRepository roleRepository) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String users(ModelMap modelMap) {
        modelMap.addAttribute("userList", userService.listUsers());
        return "users";
    }

    @GetMapping("/add")
    public String newUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("role",roleRepository.findAll());
        return "add";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Validated User user,
                             BindingResult bindingResult) {
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()) {
            return "add";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userID(@RequestParam("id") long id, ModelMap modelMap) {
        modelMap.addAttribute("userID", userService.userID(id));
        return "userID";
    }

    @GetMapping("/remove")
    public String removeUser(@RequestParam("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.userID(id));
        modelMap.addAttribute("role",roleRepository.findAll());

        return "usersEdit";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") long id,
                         @ModelAttribute("user") @Validated User user,
                         BindingResult bindingResult) {
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()) {
            return "usersEdit";
        }
        userService.update(user, id);
        return "redirect:/admin";
    }

}
