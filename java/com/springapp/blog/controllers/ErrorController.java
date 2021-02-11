package com.springapp.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String error(Model model){
        model.addAttribute("view", "error/500.html");
        model.addAttribute("message", "Oooops we have error! Please try later");

        return "base-layout";
    }
}
