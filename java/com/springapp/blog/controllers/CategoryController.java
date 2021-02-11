package com.springapp.blog.controllers;

import com.springapp.blog.dao.CategoryDao;
import com.springapp.blog.models.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        categoryDao.create(categoryModel);

        return "redirect:/admin/categories/";
    }
}
