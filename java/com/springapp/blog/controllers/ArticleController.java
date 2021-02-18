package com.springapp.blog.controllers;

import com.springapp.blog.dao.ArticleDao;
import com.springapp.blog.dao.CategoryDao;
import com.springapp.blog.dao.TagDao;
import com.springapp.blog.dao.UserDao;
import com.springapp.blog.entity.Article;
import com.springapp.blog.entity.Tag;
import com.springapp.blog.models.ArticleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleDao articleDao;
    private final CategoryDao categoryDao;
    private final TagDao tagDao;
    private final UserDao userDao;

    @Autowired
    public ArticleController(ArticleDao articleDao, CategoryDao categoryDao, TagDao tagDao, UserDao userDao) {
        this.articleDao = articleDao;
        this.categoryDao = categoryDao;
        this.tagDao = tagDao;
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public String details(Model model, @PathVariable("id") Integer id){
        Article article = articleDao.getOne(id);

        if (article == null){
            return "redirect:/";
        }

        model.addAttribute("view", "article/details");
        model.addAttribute("article", article);
        model.addAttribute("user", userDao.getUserFromUserDetails());
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

        articleDao.create(articleModel, tagDao.addNewTags(articleModel.getTags()));

        return "redirect:/category/" + articleModel.getCategory();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        if (!articleDao.isExist(id)){
            return "redirect:/";
        }
        
        if(!articleDao.isAuthor(id)){
            return "redirect:/";
        }

        Article article = articleDao.getOne(id);

        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);
        model.addAttribute("tags", article.getTags().stream().map(Tag::getName).collect(Collectors.joining(", ")));
        model.addAttribute("categories", categoryDao.list());

        return "base-layout";
    }

    @PreAuthorize("isAuthenticated")
    @PostMapping("/edit/{id}")
    public String editProcess(Model model, @PathVariable("id") Integer id, @ModelAttribute("article") @Valid ArticleModel article, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            model.addAttribute("view", "article/edit");
            model.addAttribute("tags", article.getTags());
            model.addAttribute("categories", categoryDao.list());
            return "base-layout";
        }

        articleDao.edit(id, article, tagDao.addNewTags(article.getTags()));

        return "redirect:/category/" + article.getCategory();
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id")Integer id){
        if (!articleDao.isExist(id)){
            return "redirect:/";
        }

        if(!articleDao.isAuthor(id)){
            return "redirect:/";
        }

        Article article = articleDao.getOne(id);

        model.addAttribute("view", "article/delete");
        model.addAttribute("article", article);

        return "base-layout";
    }

    @PreAuthorize("isAuthenticated")
    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable("id") Integer id){
        Integer categoryId = articleDao.getOne(id).getCategory().getId();
        articleDao.delete(id);

        return "redirect:/category/" + categoryId;
    }
}
