package com.springapp.blog.controllers;

import com.springapp.blog.dao.UserDao;
import com.springapp.blog.models.user.UserLoginModel;
import com.springapp.blog.models.user.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("view", "user/login");
        model.addAttribute("user", new UserLoginModel());

        return "base-layout";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("view", "user/register");
        model.addAttribute("user", new UserRegisterModel());

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(Model model, @ModelAttribute("user") @Valid UserRegisterModel userRegisterModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("view", "user/register");
            return "base-layout";
        }

        if(userDao.emailIsExist(userRegisterModel.getEmail())){
            bindingResult.addError(new FieldError("user", "email", "Email is already exist"));
            model.addAttribute("view", "user/register");
            return "base-layout";
        }

        if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())){
            bindingResult.addError(new FieldError("user", "password", "Password and Confirm password should be equals"));
            model.addAttribute("view", "user/register");
            return "base-layout";
        }

        userDao.register(userRegisterModel);

        return "redirect: /login";
    }
}
