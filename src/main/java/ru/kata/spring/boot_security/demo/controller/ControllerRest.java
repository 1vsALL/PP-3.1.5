package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserSecurity;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping()
public class ControllerRest {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;
    private final UserSecurity userSecurity;

    @Autowired
    public ControllerRest(UserService userService, UserValidator userValidator, RoleService roleService, UserSecurity userSecurity) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
        this.userSecurity = userSecurity;
    }

    @GetMapping("/user-data")
    public ResponseEntity<User> showUserDetails(Principal principal) {
        User user = userSecurity.findByUsername(principal.getName()).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getByID(@PathVariable("id") long id) {
        User user=userService.userID(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") long id,
                       @ModelAttribute("user") @Validated User user,
                       BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new EntityNotFoundException("проблема с валидацией");
        }
        userService.update(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/add")
    public ResponseEntity<HttpStatus> create(@ModelAttribute("newUser") @Validated User user,
                                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new EntityNotFoundException("проблема с валидацией");
        }
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> listRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }
}
