package com.tomasdev.akhanta.product.categories;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryTagServiceImpl implements CategoryTagsService {

    private final CategoryTagsRepository repository;

    @Override
    public List<CategoryTag> findAllTags() {
        return repository.findAllParentCategories();
    }

    @Override
    public void saveTag(CategoryTag categoryTag) {

        if (categoryTag.getParentCategory() != null) {

            CategoryTag parentTag = findTagById(categoryTag.getParentCategory());
            parentTag.addSubCategory(categoryTag);

            repository.saveAll(new ArrayList<>(List.of(categoryTag, parentTag)));
            return;
        }

        repository.save(categoryTag);
    }

    @Override
    public CategoryTag findTagById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Tag id \{id} no encontrado"));
    }

    @Override
    public void deleteTagById(String id) {
        repository.deleteById(id);
    }
}
