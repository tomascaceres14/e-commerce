package com.tomasdev.akhanta.product.categories;

import java.util.List;

public interface CategoryTagsService {

    List<CategoryTag> findAllTags();
    void saveTag(CategoryTag categoryTag);
    CategoryTag findTagById(String id);
    void deleteTagById(String id);
}
