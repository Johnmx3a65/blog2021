package com.springapp.blog.controllers;

import com.springapp.blog.models.UserLoginModel;
import com.springapp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("view", "user/login");
        model.addAttribute("user", new UserLoginModel());

        return "base-layout";
    }
}
