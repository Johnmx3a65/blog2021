package com.springapp.blog.controllers;

import com.springapp.blog.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/tag")
public class TagController {

    private final TagDao tagDao;

    @Autowired
    public TagController(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @GetMapping("/{name}")
    public String list(Model model, @PathVariable("name") String name){
        if(!tagDao.tagIsExist(name)){
            return "redirect:/";
        }

        model.addAttribute("view", "tag/list");
        model.addAttribute("tag", tagDao.getTagByName(name));

        return "base-layout";
    }
}
