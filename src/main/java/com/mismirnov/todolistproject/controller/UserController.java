package com.mismirnov.todolistproject.controller;


import com.mismirnov.todolistproject.entity.Role;
import com.mismirnov.todolistproject.entity.User;
import com.mismirnov.todolistproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String editUser(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "edituser";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String saveUser(
            @RequestParam List<String> roles,
            @RequestParam Map<String, String> parameters,
            @RequestParam("userId") User user
    ) {
        userService.saveUser(user, roles, parameters);

        return "redirect:/user";
    }
}