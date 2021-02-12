package com.springapp.blog.controllers.admin;

import com.springapp.blog.dao.RoleDao;
import com.springapp.blog.dao.UserDao;
import com.springapp.blog.entity.User;
import com.springapp.blog.models.user.UserEditModel;
import com.springapp.blog.models.user.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminUsersController {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public AdminUsersController(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("view", "admin/users/list");
        model.addAttribute("users", userDao.list());

        return "base-layout";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){

        User user = userDao.getOne(id);

        if (user == null){
            return "redirect:/admin/users";
        }

        model.addAttribute("view", "admin/users/edit");
        model.addAttribute("user", user);
        model.addAttribute("roles", roleDao.getAll());

        return "base-layout";
    }

    @PostMapping("/users/edit/{id}")
    public String editProcess(Model model, @PathVariable("id") Integer id, @ModelAttribute("user") @Valid UserEditModel userEditModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("view", "admin/users/edit");
            model.addAttribute("roles", roleDao.getAll());

            return "base-layout";
        }

        if(userDao.emailIsExist(userEditModel.getEmail()) && !userDao.isHimEmail(userEditModel.getEmail(), id)){
            bindingResult.addError(new FieldError("user", "email", "Email is already exist"));
            model.addAttribute("view", "admin/users/edit");
            model.addAttribute("roles", roleDao.getAll());

            return "base-layout";
        }

        if (!userEditModel.getPassword().equals(userEditModel.getConfirmPassword())){
            bindingResult.addError(new FieldError("user", "password", "Password and confirm password should be equals"));
            model.addAttribute("view", "admin/users/edit");
            model.addAttribute("roles", roleDao.getAll());

            return "base-layout";
        }

        userDao.edit(id, userEditModel);

        return "redirect:/admin/users";
    }
}
