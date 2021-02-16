package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.dao.CategoryDao;
import com.springapp.blog.entity.Article;
import com.springapp.blog.models.ArticleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleDao articleDao;
    private final CategoryDao categoryDao;

    @Autowired
    public ArticleController(ArticleDao articleDao, CategoryDao categoryDao) {
        this.articleDao = articleDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/{id}")
    public String details(Model model, @PathVariable("id") Integer id){
        Article article = articleDao.getOne(id);

        if (article == null){
            return "redirect:/";
        }

        model.addAttribute("view", "article/details");
        model.addAttribute("article", article);
        model.addAttribute("author", article.getAuthor());

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "article/create");
        model.addAttribute("article", new ArticleModel());
        model.addAttribute("categories", categoryDao.list());

        return "base-layout";
    }

}
