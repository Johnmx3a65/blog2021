package com.springapp.blog.controllers;

import com.springapp.blog.dao.CategoryDao;
import com.springapp.blog.models.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin/categories")
public class CategoryController {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("view", "admin/categories/list");
        model.addAttribute("categories", categoryDao.list());

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "admin/categories/create");
        model.addAttribute("category", new CategoryModel());

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(Model model, @ModelAttribute("category") CategoryModel categoryModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("view", "admin/categories/create");
            return "base-layout";
        }

        if(categoryDao.isAlreadyExist(categoryModel.getName())){
            bindingResult.addError(new FieldError("category", "name", "Name is already exist"));
            model.addAttribute("view", "admin/categories/create");
            return "base-layout";
        }

        categoryDao.create(categoryModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        model.addAttribute("view", "admin/categories/edit");
        model.addAttribute("category", categoryDao.getOne(id));

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(Model model, @PathVariable("id") Integer id, @ModelAttribute("category") CategoryModel categoryModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("view", "admin/categories/edit");
            return "base-layout";
        }

        categoryDao.edit(id, categoryModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") Integer id){
        model.addAttribute("view", "admin/categories/delete");
        model.addAttribute("category", categoryDao.getOne(id));

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable("id") Integer id){
        categoryDao.delete(id);

        return "redirect:/admin/categories/";
    }
}
