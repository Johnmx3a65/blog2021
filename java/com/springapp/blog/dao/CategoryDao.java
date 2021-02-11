package com.springapp.blog.dao;

import com.springapp.blog.entity.Category;
import com.springapp.blog.models.CategoryModel;
import com.springapp.blog.repository.ArticleRepository;
import com.springapp.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class CategoryDao {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CategoryDao(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    public List<Category> list(){
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparingInt(Category::getId));
        return categories;
    }

    public void create(CategoryModel categoryModel){
        Category category = new Category(categoryModel.getName());

        categoryRepository.saveAndFlush(category);
    }

    public void edit(Integer id, CategoryModel categoryModel){

        Category category = categoryRepository.getOne(id);

        category.setName(categoryModel.getName());

        categoryRepository.saveAndFlush(category);
    }

    public Category getOne(Integer id){
        return categoryRepository.getOne(id);
    }
    
    public void delete(Integer id){
        articleRepository.deleteAllByCategory(categoryRepository.getOne(id));
        categoryRepository.deleteById(id);
    }
}
