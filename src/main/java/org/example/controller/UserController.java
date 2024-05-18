package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}/phone")
    public User updatePhone(@PathVariable Long id, @RequestBody String newPhone) {
        return userService.updatePhone(id, newPhone);
    }

    @PutMapping("/{id}/email")
    public User updateEmail(@PathVariable Long id, @RequestBody String newEmail) {
        return userService.updateEmail(id, newEmail);
    }
}