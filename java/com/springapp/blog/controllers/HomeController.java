package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listArticles(Model model, @PathVariable("id") Integer id, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset){
        model.addAttribute("view", "home/list-articles");
        model.addAttribute("category", categoryDao.getOne(id));
        model.addAttribute("articles", articleDao.getLimitOffsetByCategory(id, limit, offset));

        return "base-layout";
    }
}
