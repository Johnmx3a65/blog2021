package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.dao.UserDao;
import com.springapp.blog.models.user.UserLoginModel;
import com.springapp.blog.models.user.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {

    private final UserDao userDao;
    private final ArticleDao articleDao;

    @Autowired
    public UserController(UserDao userDao, ArticleDao articleDao) {
        this.userDao = userDao;
        this.articleDao = articleDao;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("view", "user/login");
        model.addAttribute("user", new UserLoginModel());

        return "base-layout";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        userDao.logout(request, response);
        return "redirect:/login?logout";
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

        return "redirect:/login";
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("view", "user/profile");
        model.addAttribute("articles", articleDao.profile());

        return "base-layout";
    }
}
