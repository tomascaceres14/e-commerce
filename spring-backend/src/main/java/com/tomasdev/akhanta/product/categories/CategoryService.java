package com.tomasdev.akhanta.product.categories;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();
    void saveCategory(Category category);
    Category findCategoryById(String id);

    void deleteCategoryById(String id);
}
