package com.springapp.blog.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ArticleModel {
    private Integer id;

    @NotEmpty(message = "Article should not be empty")
    private String title;

    @NotEmpty(message = "Content should not be empty")
    private String content;

    @Pattern(regexp = "(([A-Za-z]+,\\s*)*([A-Za-z]+($|\\s*)))", message = "Tags should match the pattern [tag1,tag2,tag3...]")
    private String tags;

    private Integer category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
