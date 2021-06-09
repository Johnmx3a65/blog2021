package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final CategoryDao categoryDao;
    private final ArticleDao articleDao;

    @Autowired
    public HomeController(CategoryDao categoryDao, ArticleDao articleDao) {
        this.categoryDao = categoryDao;
        this.articleDao = articleDao;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("view", "home/index");
        model.addAttribute("categories", categoryDao.list());

        return "base-layout";
    }

    @GetMapping("/category/{id}")
    public String listArticles(Model model, @PathVariable("id") Integer id){
        model.addAttribute("view", "home/list-articles");
        model.addAttribute("category", categoryDao.getOne(id));
        model.addAttribute("articles", articleDao.getLimitOffsetByCategory(id, 5, 0));

        return "base-layout";
    }
}
