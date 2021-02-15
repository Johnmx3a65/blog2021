package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.entity.Article;
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

    @Autowired
    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
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

}
