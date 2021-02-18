package com.springapp.blog.repository;


import com.springapp.blog.entity.Article;
import com.springapp.blog.entity.Category;
import com.springapp.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByAuthor(User user);

    void deleteAllByCategory(Category category);

    void deleteAllByAuthor(User user);
}
