package com.tomasdev.akhanta.product.categories;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> findAllCategories() {
        return repository.findAllParentCategories();
    }

    @Override
    public void saveCategory(Category category) {
        repository.save(category);
    }

    @Override
    public Category findCategoryById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Category id \{id} no encontrado"));
    }

    @Override
    public void deleteCategoryById(String id) {
        repository.deleteById(id);
    }
}
