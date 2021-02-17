package com.springapp.blog.dao;

import com.springapp.blog.entity.Tag;
import com.springapp.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Component
public class TagsDao {

    private final TagRepository tagRepository;

    @Autowired
    public TagsDao(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> addNewTags(@org.jetbrains.annotations.NotNull String tags) {

        List<Tag> tagHashSet = new LinkedList<>();

        String[] tagNames = tags.split(",\\s*");

        for (String tagName : tagNames){
            if(!tagName.equals("")){
                Tag currentTag = this.tagRepository.findByName(tagName);

                if(currentTag == null){
                    currentTag = new Tag(tagName);
                    this.tagRepository.saveAndFlush(currentTag);
                }

                tagHashSet.add(currentTag);
            }
        }
        return tagHashSet;
    }
}
