package com.springapp.blog.models;

import javax.validation.constraints.NotEmpty;

public class ArticleModel {

    @NotEmpty(message = "Article should not be empty")
    private String title;

    @NotEmpty(message = "Content should not be empty")
    private String content;

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
}
