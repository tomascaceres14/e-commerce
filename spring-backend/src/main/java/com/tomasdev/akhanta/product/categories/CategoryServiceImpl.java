package com.tomasdev.akhanta.product.categories;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<Category> findAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category findCategoryById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria"));
    }

    @Override
    public void saveCategory(Category category) {
        repository.save(category);
    }

    @Override
    public Category findCategoryByNode(String node) {
        return repository.findCategoryByNode(node).orElseThrow(() -> new ResourceNotFoundException(STR."Categoría de nodo \{node} no encontrada."));
    }

    @Override
    public Category updateById(String id, Category category) {
        Category categoryDB = findCategoryById(id);
        mapper.map(category, categoryDB);
        return repository.save(categoryDB);
    }

    @Override
    public void deleteCategoryById(String id) {
        repository.deleteById(id);
    }
}
