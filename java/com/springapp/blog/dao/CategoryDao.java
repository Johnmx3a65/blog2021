package com.springapp.blog.dao;

import com.springapp.blog.entity.Category;
import com.springapp.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDao {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryDao(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list(){
        return categoryRepository.findAll();
    }
}
