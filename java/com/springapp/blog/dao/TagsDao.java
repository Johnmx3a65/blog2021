package com.springapp.blog.dao;

import com.springapp.blog.entity.Tag;
import com.springapp.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class TagsDao {

    private final TagRepository tagRepository;

    @Autowired
    public TagsDao(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public HashSet<Tag> addNewTags(@org.jetbrains.annotations.NotNull String tags) {

        HashSet<Tag> tagHashSet = new HashSet<>();

        String[] tagNames = tags.split("\\s");

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
