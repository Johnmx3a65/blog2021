package com.springapp.blog.controllers;

import com.springapp.blog.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
