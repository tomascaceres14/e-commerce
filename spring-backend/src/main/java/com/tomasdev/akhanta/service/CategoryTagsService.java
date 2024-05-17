package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.CategoryTag;

import java.util.List;

public interface CategoryTagsService {

    List<CategoryTag> findAllTags();
    void saveTag(CategoryTag categoryTag);
    CategoryTag findTagById(String id);
    void deleteTagById(String id);
}
