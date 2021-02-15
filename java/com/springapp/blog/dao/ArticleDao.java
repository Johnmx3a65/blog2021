package com.springapp.blog.dao;

import com.springapp.blog.entity.Article;
import com.springapp.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleDao {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleDao(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article getOne(Integer id){
        return articleRepository.findById(id).orElse(null);
    }
}
