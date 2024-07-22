package com.tomasdev.akhanta.product.categories;

import java.util.List;

public interface CategoryService {

    void saveCategory(Category category);
    List<Category> findAllCategories();
    Category findCategoryById(String id);
    Category findCategoryByNode(String node);
    Category updateById(String id, Category category);
    void deleteCategoryById(String id);
}
