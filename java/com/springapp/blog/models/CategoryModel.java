package com.springapp.blog.models;

import javax.validation.constraints.NotEmpty;

public class CategoryModel {

    @NotEmpty(message = "Name should not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
