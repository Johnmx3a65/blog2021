package com.springapp.blog.repository;


import com.springapp.blog.entity.Article;
import com.springapp.blog.entity.Category;
import com.springapp.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByAuthor(User user);

    void deleteAllByCategory(Category category);

    void deleteAllByAuthor(User user);

    @Query(value = "SELECT * FROM articles WHERE articles.category_id = ?1 LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<Article> findLimitOffsetByCategory(int category_id, int limit, int offset);
}
