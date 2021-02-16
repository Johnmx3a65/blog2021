package com.springapp.blog.dao;

import com.springapp.blog.entity.Article;
import com.springapp.blog.entity.Category;
import com.springapp.blog.entity.Tag;
import com.springapp.blog.entity.User;
import com.springapp.blog.models.ArticleModel;
import com.springapp.blog.repository.ArticleRepository;
import com.springapp.blog.repository.CategoryRepository;
import com.springapp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ArticleDao {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleDao(ArticleRepository articleRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Article getOne(Integer id){
        return articleRepository.findById(id).orElse(null);
    }

    public void create(ArticleModel articleModel, HashSet<Tag> tags){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        Category category = categoryRepository.getOne(articleModel.getCategory());
        Article article = new Article(articleModel.getTitle(), articleModel.getContent(), user, category, tags);

        articleRepository.saveAndFlush(article);
    }
}
