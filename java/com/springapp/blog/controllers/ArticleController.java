package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.dao.CategoryDao;
import com.springapp.blog.dao.TagsDao;
import com.springapp.blog.entity.Article;
import com.springapp.blog.models.ArticleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleDao articleDao;
    private final CategoryDao categoryDao;
    private final TagsDao tagsDao;

    @Autowired
    public ArticleController(ArticleDao articleDao, CategoryDao categoryDao, TagsDao tagsDao) {
        this.articleDao = articleDao;
        this.categoryDao = categoryDao;
        this.tagsDao = tagsDao;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "article/create");
        model.addAttribute("article", new ArticleModel());
        model.addAttribute("categories", categoryDao.list());

        return "base-layout";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createProcess(Model model, @ModelAttribute("article") @Valid ArticleModel articleModel, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            model.addAttribute("view", "article/create");
            model.addAttribute("categories", categoryDao.list());
            return "base-layout";
        }

        articleDao.create(articleModel, tagsDao.addNewTags(articleModel.getTags()));

        return "redirect:/";
    }


}
